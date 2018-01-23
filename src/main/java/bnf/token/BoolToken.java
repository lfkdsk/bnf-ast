package bnf.token;


import ast.base.AstLeaf;
import ast.token.Token;

public class BoolToken extends AToken {

    public BoolToken(Class<? extends AstLeaf> clazz) {
        super(clazz);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isBool();
    }
}
