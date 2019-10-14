package bnfgenast.bnf.leaf;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.IDLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class SkipORTest {

    @Test
    public void testSkipOR() {
        Lexer lexer = new JustLexer("lfkdsk*");

        BnfCom id = rule().identifier(IDLiteral::new, lexer.getReservedToken());
        BnfCom token = rule().then(id).maybe("*");

        AstNode node = token.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "lfkdsk");

        lexer = new JustLexer("lfkdsk");
        node = token.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "lfkdsk");
    }
}