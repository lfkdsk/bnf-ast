package lexer;


import ast.token.Token;
import exception.ParseException;

import java.util.Queue;
import java.util.Set;

public interface Lexer {
    /**
     * 读取Token队列的下一个Token
     *
     * @return 返回第一个Token
     * @throws ParseException
     */
    Token read() throws ParseException;

    /**
     * 获取队列中的某个指定位置的Token
     *
     * @param index num
     * @return 返回Token
     * @throws ParseException
     */
    Token peek(int index) throws ParseException;

    /**
     * Get All tokens once time
     * # Better to Debug or Use tokens
     *
     * @return all-tokens
     */
    Queue<Token> tokens();

    void reserved(String token);

    void backup();

    void recover();

    Set<String> getReservedToken();

    boolean isHasMore();

    int getLineNumber();
}
