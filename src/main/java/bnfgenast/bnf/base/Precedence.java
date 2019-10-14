package bnfgenast.bnf.base;


import bnfgenast.ast.base.AstNode;

import java.util.List;
import java.util.function.Function;

public final class Precedence {
    public final int value;
    public final boolean leftAssoc;
    public final Function<List<AstNode>, ? extends AstNode> factory;

    Precedence(int value, boolean leftAssoc,
               Function<List<AstNode>, ? extends AstNode> factory) {
        this.value = value;
        this.leftAssoc = leftAssoc;
        this.factory = factory;
    }
}
