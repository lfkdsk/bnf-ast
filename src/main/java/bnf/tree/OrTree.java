package bnf.tree;


import ast.base.AstNode;
import bnf.BnfCom;
import bnf.base.Element;
import exception.ParseException;
import lexer.Lexer;

import java.util.List;

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
    public void parse(Lexer lexer, List<AstNode> nodes) throws ParseException {
        BnfCom parser = choose(lexer);
        if (parser == null) {
            throw new ParseException(lexer.peek(0));
        } else {
            nodes.add(parser.parse(lexer));
        }
    }

    @Override
    public boolean match(Lexer lexer) throws ParseException {
        return choose(lexer) != null;
    }

    protected BnfCom choose(Lexer lexer) throws ParseException {
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
