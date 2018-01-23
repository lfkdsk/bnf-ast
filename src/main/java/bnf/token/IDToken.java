package bnf.token;


import ast.base.AstLeaf;
import ast.token.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * ID 类型的Token
 */
public class IDToken extends AToken {
    Set<String> reserved;

    public IDToken(Class<? extends AstLeaf> clazz, Set<String> reserved) {
        super(clazz);
        this.reserved = reserved != null ? reserved : new HashSet<>();
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isIdentifier() && !reserved.contains(token.getText());
    }
}
