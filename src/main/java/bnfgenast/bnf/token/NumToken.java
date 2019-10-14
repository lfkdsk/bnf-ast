package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.base.AstLeafCreator;

import java.util.function.Function;

/**
 * 数字类型Token
 */
public class NumToken extends AToken {

    public NumToken(AstLeafCreator<? extends AstNode> factory) {
        super(factory);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isNumber();
    }
}
