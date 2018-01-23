package bnf.tree;


import ast.base.AstList;
import ast.base.AstNode;
import bnf.BnfCom;
import bnf.base.Element;
import exception.ParseException;
import lexer.Lexer;

import java.util.List;

/**
 * 重复出现的语句节点
 * 比如block中会出现多次的simple
 * 还有Option
 */
public class Repeat extends Element {
    protected BnfCom parser;

    protected boolean onlyOne;

    /**
     * @param parser  BNF
     * @param onlyOne 节点出现次数
     */
    public Repeat(BnfCom parser, boolean onlyOne) {
        this.parser = parser;
        this.onlyOne = onlyOne;
    }

    @Override
    public void parse(Lexer lexer, List<AstNode> nodes) throws ParseException {
        while (parser.match(lexer)) {

            AstNode node = parser.parse(lexer);
            // token or list
            if (node.getClass() != AstList.class || node.childCount() > 0) {
                nodes.add(node);
            }

            if (onlyOne)
                break;
        }
    }

    @Override
    public boolean match(Lexer lexer) throws ParseException {
        return parser.match(lexer);
    }
}
