package bnfgenast.bnf.base;


import bnfgenast.ast.base.AstNode;

public final class Precedence {
    public final int value;
    public final boolean leftAssoc;
    public final Class<? extends AstNode> clazz;
    public final Factory factory;

    Precedence(int value, boolean leftAssoc,
               Class<? extends AstNode> clazz) {
        this.value = value;
        this.leftAssoc = leftAssoc;
        this.clazz = clazz;
        this.factory = Factory.getForAstList(clazz);
    }
}
