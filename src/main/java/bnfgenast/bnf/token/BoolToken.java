package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.token.Token;

import java.util.function.Function;

public class BoolToken extends AToken {

    public BoolToken(Function<Token, ? extends AstLeaf> factory) {
        super(factory);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isBool();
    }
}
