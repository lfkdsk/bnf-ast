package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;
import static org.junit.Assert.*;

public class PrefixTreeTest {

    @Test
    public void testPrefixTree() {
        BnfCom rule1 = rule().token("lfkdsk").token("[").name("rule1");
        BnfCom rule2 = rule().token("lfkdsk").token("<").name("rule2");
        BnfCom rule3 = rule().token("lfkdsk").token("{").name("rule3");

        BnfCom ruleCombinator = rule().prefix(rule1, rule2, rule3);

        Lexer lexer = new JustLexer("lfkdsk {");

        lexer.reserved("lfkdsk");
        lexer.reserved("{");
        lexer.reserved("<");
        lexer.reserved("[");

        AstNode node = ruleCombinator.parse(lexer);
        Assert.assertNotNull(node);
        Assert.assertEquals(node.childCount(), 2);
        Assert.assertTrue(node.child(0) instanceof AstLeaf);
        Assert.assertTrue(node.child(1) instanceof AstLeaf);

        Assert.assertEquals(((AstLeaf) node.child(0)).token().getText(), "lfkdsk");
        Assert.assertEquals(((AstLeaf) node.child(1)).token().getText(), "{");
    }
}