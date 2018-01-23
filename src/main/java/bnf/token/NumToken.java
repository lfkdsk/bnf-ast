package bnf.token;


import ast.base.AstLeaf;
import ast.token.Token;

/**
 * 数字类型Token
 */
public class NumToken extends AToken {

    public NumToken(Class<? extends AstLeaf> clazz) {
        super(clazz);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isNumber();
    }

}
