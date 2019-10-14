package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.token.Token;

import java.util.function.Function;

/**
 * 数字类型Token
 */
public class NumToken extends AToken {

    public NumToken(Function<Token, ? extends AstLeaf> factory) {
        super(factory);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isNumber();
    }
}
