package bnfgenast.bnf.tree;


import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;

/**
 * 开一棵子树
 * Tree中并没有对处理细节的描述
 * 只是个构造基类
 */
public class Tree extends Element {
    protected BnfCom parser;

    public Tree(BnfCom parser) {
        this.parser = parser;
    }

    @Override
    public void parse(Lexer lexer, List<AstNode> nodes) throws ParseException {
        nodes.add(parser.parse(lexer));
    }

    @Override
    public boolean match(Lexer lexer) throws ParseException {
        return parser.match(lexer);
    }
}
