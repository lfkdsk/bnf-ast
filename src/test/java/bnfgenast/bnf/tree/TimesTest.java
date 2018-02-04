package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class TimesTest {

    @Test(expected = IllegalArgumentException.class)
    public void testTimes() {
        BnfCom times = rule().token("lfkdsk").times(
                rule().sep(",").token("lfkdsk"),
                3
        );

        Lexer lexer1 = new JustLexer("lfkdsk,lfkdsk,lfkdsk");

        AstNode node = times.parse(lexer1);
        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstList);
        Assert.assertEquals(node.childCount(), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMostTimes() {
        // most is 1 but predicate is 2
        BnfCom times = rule().token("lfkdsk").most(
                rule().sep(",").token("lfkdsk"),
                1
        );

        Lexer lexer1 = new JustLexer("lfkdsk,lfkdsk,lfkdsk");

        AstNode node = times.parse(lexer1);
        Assert.assertNotNull(node);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLeaseTimes() {
        // least is 3 but predicate is 2
        BnfCom times = rule().token("lfkdsk").least(
                rule().sep(",").token("lfkdsk"),
                3
        );

        Lexer lexer1 = new JustLexer("lfkdsk,lfkdsk,lfkdsk");

        AstNode node = times.parse(lexer1);
        Assert.assertNotNull(node);
    }

    @Test
    public void testRange() {
        // 3-5
        BnfCom times = rule().token("lfkdsk").range(
                rule().sep(",").token("lfkdsk"),
                3,5
        );

        Lexer lexer1 = new JustLexer("lfkdsk,lfkdsk,lfkdsk,lfkdsk,lfkdsk");

        AstNode node = times.parse(lexer1);
        Assert.assertNotNull(node);
        Assert.assertEquals(5, node.childCount());
    }
}