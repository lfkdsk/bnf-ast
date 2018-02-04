package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.BoolLiteral;
import lisp.literal.NumberLiteral;
import lisp.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

public class InsertChoiceTest {

    @Test(expected = ParseException.class)
    public void testInsertChoice() {
        BnfCom number = rule().number(NumberLiteral.class);
        BnfCom bool = rule().bool(BoolLiteral.class);

        // number | string | bool
        BnfCom primary = wrapper().or(number, bool);

        Lexer lexer = new JustLexer("\"lfkdsk\"");
        AstNode node = primary.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstLeaf);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "lfkdsk");
    }

    @Test
    public void testInsertChoice1() {
        BnfCom number = rule().number(NumberLiteral.class);
        BnfCom bool = rule().bool(BoolLiteral.class);

        // number | string | bool
        BnfCom primary = wrapper().or(number, bool);

        BnfCom string = rule().string(StringLiteral.class);

        primary.insertChoice(string);

        Lexer lexer = new JustLexer("\"lfkdsk\"");
        AstNode node = primary.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstLeaf);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "lfkdsk");
    }
}