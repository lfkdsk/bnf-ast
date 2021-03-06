package bnfgenast.bnf.token;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.token.Token;

import java.util.function.Function;

/**
 * 字符串类型Token
 */
public class StrToken extends AToken {

    public StrToken(Function<Token, ? extends AstLeaf> factory) {
        super(factory);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isString();
    }
}
