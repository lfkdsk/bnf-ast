package bnfgenast.bnf.leaf;


import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;

import java.util.List;

public class Skip extends Leaf {

    public Skip(String[] pat) {
        super(pat);
    }

    /**
     * 所谓Skip 不添加节点
     * 比如一些格式控制符号是不算做节点的
     *
     * @param list  list
     * @param token token
     */
    @Override
    protected void find(List<AstNode> list, Token token) {

    }
}
