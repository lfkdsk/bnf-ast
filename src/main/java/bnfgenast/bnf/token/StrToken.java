package bnfgenast.bnf.token;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.token.Token;
/**
 * 字符串类型Token
 */
public class StrToken extends AToken {

    public StrToken(Class<? extends AstLeaf> clazz) {
        super(clazz);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isString();
    }
}
