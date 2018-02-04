package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

public class AssertCaptureTest {

    @Test
    public void testAssertCapture() {
        BnfCom leaf = wrapper().predicate(rule().token("lfkdsk"), node -> node instanceof AstLeaf);

        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");

        AstNode node = leaf.parse(lexer);
        Assert.assertNotNull(node);
    }
}