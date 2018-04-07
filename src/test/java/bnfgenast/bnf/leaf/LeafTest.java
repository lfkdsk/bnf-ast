package bnfgenast.bnf.leaf;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class LeafTest {

    @Test
    public void testLeaf() {
        BnfCom string = rule().string(StringLiteral.class);
        BnfCom token = rule().then(string).token("==").then(string);

        Lexer lexer = new JustLexer("\"lfkdsk\" == \"123\"");
        lexer.reserved("==");

        AstNode node = token.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(node.childCount(), 3);
        Assert.assertEquals(((AstLeaf)node.child(1)).token().getText(), "==");
    }
}