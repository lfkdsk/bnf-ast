package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

import static bnfgenast.bnf.BnfCom.rule;

public class PredicateCaptureTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNotPredicate() {
        BnfCom leaf = rule().token("lfkdsk")
                            .not((Predicate<AstLeaf>) node -> node.token().getText().equals("lfkdsk"));

        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");

        AstNode node = leaf.parse(lexer.tokens());
        Assert.assertNotNull(node);
    }

    @Test()
    public void testPredicate() {
        BnfCom leaf = rule().token("if")
                            .test((Predicate<AstLeaf>) node -> node.token().getText().equals("if"))
                            .token("bool");

        Lexer lexer = new JustLexer("if bool");
        lexer.reserved("if");
        lexer.reserved("bool");

        AstNode node = leaf.parse(lexer.tokens());
        Assert.assertNotNull(node);
    }
}