package bnfgenast.bnf.tree;


import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;
import java.util.Queue;

/**
 * BNF 产生式中的 或节点
 * [] | []
 */
public class OrTree extends Element {
    protected BnfCom[] parsers;

    public OrTree(BnfCom[] parsers) {
        this.parsers = parsers;
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        BnfCom parser = choose(lexer);
        if (parser == null) {
            throw new ParseException(lexer.peek());
        } else {
            nodes.add(parser.parse(lexer));
        }
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return choose(lexer) != null;
    }

    protected BnfCom choose(Queue<Token> lexer) throws ParseException {
        for (BnfCom parser : parsers) {
            if (parser.match(lexer)) {
                return parser;
            }
        }
        return null;
    }

    /**
     * 插入节点 插在了0
     *
     * @param parser BNF
     */
    public void insert(BnfCom parser) {
        BnfCom[] newParsers = new BnfCom[parsers.length + 1];
        newParsers[0] = parser;
        System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
        parsers = newParsers;
    }
}
