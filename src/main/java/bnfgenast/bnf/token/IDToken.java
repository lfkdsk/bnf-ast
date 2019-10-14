package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.base.AstLeafCreator;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * ID 类型的Token
 */
public class IDToken extends AToken {
    private Set<String> reserved;

    public IDToken(AstLeafCreator<? extends AstNode> factory, Set<String> reserved) {
        super(factory);
        this.reserved = reserved != null ? reserved : new HashSet<>();
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isIdentifier() && !reserved.contains(token.getText());
    }
}
