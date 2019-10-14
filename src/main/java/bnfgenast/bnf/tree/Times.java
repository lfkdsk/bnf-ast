package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;
import bnfgenast.utils.ObjectHelper;

import java.util.List;
import java.util.Queue;

public class Times extends Element {

    protected BnfCom parser;

    private int acceptTimes;

    private int times;

    private int maxTimes = Integer.MAX_VALUE;

    private int minTimes = Integer.MIN_VALUE;

    private boolean acceptType;

    public Times(BnfCom parser, int times) {
        this.parser = parser;
        this.acceptTimes = ObjectHelper.verifyPositive(times, "times");
        this.times = 0;
        this.acceptType = true;
    }

    public Times(BnfCom parser, int acceptTimes, int maxTimes, int minTimes) {
        this(parser, acceptTimes);
        this.maxTimes = ObjectHelper.verifyPositive(maxTimes, "maxTimes");
        this.minTimes = ObjectHelper.verifyPositive(minTimes, "minTimes");
        this.acceptType = false;
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        while (acceptTimes != 0 && parser.match(lexer)) {

            AstNode node = parser.parse(lexer);
            // token or list
            if (node.getClass() != AstList.class || node.childCount() > 0) {
                nodes.add(node);
            }

            if (acceptTimes == 0) {
                break;
            }

            acceptTimes--;
            times++;
        }

        if (times < minTimes) {
            throw new IllegalArgumentException("run times less than minTimes : (times):" + times + " (min-times):" + minTimes);
        }

        if (times > maxTimes) {
            throw new IllegalArgumentException("run times more than minTimes : (times):" + times + " (max-times):" + maxTimes);
        }

        if (acceptType && acceptTimes != 0) {
            throw new IllegalArgumentException("run times less than accept times : (times):" + times + " (left-times):" + acceptTimes);
        }
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return parser.match(lexer);
    }
}
