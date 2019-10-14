package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class RepeatTest {

    @Test
    public void testRepeat() {
        BnfCom params = rule().token("lfkdsk").repeat(
                rule().sep(",").token("llll")
        );

        Lexer lexer = new JustLexer("lfkdsk,llll,llll");
        lexer.reserved("llll");
        lexer.reserved("lfkdsk");

        AstNode node = params.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(node.childCount(), 3);
        Assert.assertTrue(node instanceof AstList);
    }
}
