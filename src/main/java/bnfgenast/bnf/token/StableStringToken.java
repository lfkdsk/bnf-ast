package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.token.Token;

import java.util.function.Function;

public class StableStringToken extends AToken {

    private String value;

    public StableStringToken(Function<Token, ? extends AstLeaf> factory, String value) {
        super(factory);
        this.value = value;
    }

    @Override
    protected boolean tokenTest(Token token) {
        return token.getText().equals(value);
    }
}
