package bnfgenast.bnf.token;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import lexer.JustLexer;
import lisp.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class LiteralTest {

    @Test
    public void testStableLiteral() {
        BnfCom literal = rule().literal(StringLiteral.class, "lfkdsk");
        JustLexer lexer = new JustLexer("lfkdsk");

        AstNode node = literal.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "lfkdsk");
    }
}
