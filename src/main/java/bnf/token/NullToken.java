package bnf.token;


import ast.base.AstLeaf;
import ast.token.Token;

public class NullToken extends AToken {

    public NullToken(Class<? extends AstLeaf> clazz) {
        super(clazz);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isNull();
    }
}
