package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.token.Token;

public class StableStringToken extends AToken {

    private String value;

    public StableStringToken(Class<? extends AstLeaf> clazz, String value) {
        super(clazz);
        this.value = value;
    }

    @Override
    protected boolean tokenTest(Token token) {
        return token.getText().equals(value);
    }
}
