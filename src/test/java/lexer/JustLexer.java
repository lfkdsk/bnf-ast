package lexer;


import bnfgenast.ast.token.Token;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;
import lombok.Getter;
import token.*;
import utils.NumberUtils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bnfgenast.utils.TextUtils.toStringLiteral;
import static lexer.JustRegex.hobbyReg;

/**
 * JustLexer 分词器
 * Use read() && peek() to get next token
 * Use tokens() to get all tokens one time
 *
 * @author liufengkai
 */
public class JustLexer implements Lexer {
    private final Pattern regPattern = Pattern.compile(hobbyReg);

    private final LinkedList<Token> queue = new LinkedList<>();

    private final ArrayList<String> avoid = new ArrayList<>();

    @Getter
    private final Set<String> reservedToken = new HashSet<>();

    @Getter
    private boolean hasMore;

    private LineNumberReader reader;

    @Getter
    private int lineNumber;

    private JustLexer backup;

    private JustLexer(JustLexer lexer) {
        // add to queue
        this.queue.clear();
        this.queue.addAll(lexer.queue);

        this.hasMore = lexer.hasMore;
        this.lineNumber = lexer.reader.getLineNumber();
    }

    /**
     * 构造
     *
     * @param reader 传入的Reader加载字符流
     */
    public JustLexer(Reader reader) {
        this.hasMore = true;
        this.reader = new LineNumberReader(reader);
        this.lineNumber = this.reader.getLineNumber();
        this.initial();
    }

    public JustLexer(String program) {
        this(new StringReader(program));
    }

    private void initial() {
        this.avoid.add("\\t");
        this.avoid.add("\\r");
        this.avoid.add("\\n");
    }

    /**
     * 读取Token队列的下一个Token
     *
     * @return 返回第一个Token
     * @throws ParseException
     */
    @Override
    public Token read() throws ParseException {
        if (fillQueue(0)) {
            return queue.remove(0);
        } else {
            return Token.EOF;
        }
    }


    /**
     * 获取队列中的某个指定位置的Token
     *
     * @param index num
     * @return 返回Token
     * @throws ParseException
     */
    @Override
    public Token peek(int index) throws ParseException {
        if (fillQueue(index)) {
            return queue.get(index);
        } else {
            return Token.EOF;
        }
    }

    /**
     * 填充队列
     *
     * @param index 指定num
     * @return 返回状态
     * @throws ParseException
     */
    private boolean fillQueue(int index) throws ParseException {
        while (index >= queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 成行读取
     *
     * @throws ParseException
     */
    private void readLine() throws ParseException {
        String line;

        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }

        if (line == null) {
            hasMore = false;
            return;
        }

        int lineNum = this.lineNumber = reader.getLineNumber();

        Matcher matcher = regPattern.matcher(line);

        /*
          1.透明边界:允许环视这样就能避免一些词素匹配混乱
          2.匹配边界:不允许正则里面包含对边界的限定符
         */
        matcher.useTransparentBounds(true)
               .useAnchoringBounds(false);

        int start = 0, end = line.length();

        if (end == 0) return;

        while (start < end) {
            matcher.region(start, end);
            // 出现匹配
            if (matcher.lookingAt()) {
                // add...
                addToken(lineNum, matcher);

                start = matcher.end();
            } else {
                throw new ParseException("bad token at line " + lineNum);
            }
        }
    }

    /**
     * 通过匹配模式判断词素类型
     *
     * @param lineNum 行号
     * @param matcher matcher
     */
    private void addToken(int lineNum, Matcher matcher) {
        String attach = matcher.group();

        if (attach == null) {
            return;
        }

        String annotation = matcher.group("ANNOTATION");

        if (annotation != null) {
            return;
        }

        String floatToken = matcher.group("FLOAT");

        if (floatToken != null) {
            int checkedType;
            Number checkedNum;

            // float or double
            if (floatToken.contains(".") || floatToken.contains("e") || floatToken.contains("E")) {
                checkedNum = NumberUtils.parseDouble(floatToken);
                checkedType = Token.FLOAT;

                if (checkedNum instanceof Double) {
                    checkedType = Token.DOUBLE;
                }
                // int or long
            } else {
                checkedNum = NumberUtils.parseLong(floatToken);
                checkedType = Token.INTEGER;

                if (checkedNum instanceof Long) {
                    checkedType = Token.LONG;
                }
            }

            queue.add(new NumberToken(lineNum, checkedType, floatToken, checkedNum));

            return;
        }

        String bool = matcher.group("BOOL");

        if (bool != null) {
            queue.add(new BoolToken(lineNum, BoolToken.booleanValue(bool)));

            return;
        }

        String string = matcher.group("STRING");

        if (string != null) {

            if (reservedToken.contains(string)) {
                queue.add(new ReservedToken(lineNum, string));
                return;
            }

            queue.add(new StringToken(lineNum, toStringLiteral(string)));

            return;
        }

        String variable = matcher.group("TOKEN");

        if (variable != null) {
            if (reservedToken.contains(variable)) {
                queue.add(new ReservedToken(lineNum, variable));
            } else {
                queue.add(new IDToken(lineNum, variable));
            }

            return;
        }

        String symbol = matcher.group("SYMBOL");

        if (symbol != null) {

            // special resolve => " " | "\t" | "\n" | "\r" => useless message
            if (avoid.contains(symbol)) {
                return;
            }

            queue.add(new SepToken(lineNum, -1, symbol));
        }
    }

    /**
     * Get All tokens once time
     * # Better to Debug or Use tokens
     *
     * @return all-tokens
     */
    @Override
    public Queue<Token> tokens() {
        while (hasMore) {
            readLine();
        }

        return queue;
    }

    @Override
    public void reserved(String token) {
        this.reservedToken.add(token);
    }

    @Override
    public void backup() {
        this.backup = new JustLexer(this);
    }

    @Override
    public void recover() {
        if (backup != null) {
            this.reader.setLineNumber(backup.lineNumber);
            // clear all && add all
            this.queue.clear();
            this.queue.addAll(backup.queue);
            this.lineNumber = backup.lineNumber;
            this.hasMore = backup.hasMore;
        }
    }
}
