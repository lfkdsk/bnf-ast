package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.BoolLiteral;
import lisp.literal.IDLiteral;
import lisp.literal.NumberLiteral;
import lisp.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;
import static org.junit.Assert.*;

public class OrTreeTest {

    @Test
    public void testOrTree() {
        BnfCom number = rule().number(NumberLiteral.class);
        BnfCom string = rule().string(StringLiteral.class);
        BnfCom bool = rule().bool(BoolLiteral.class);

        // number | string | bool
        BnfCom primary = wrapper().or(number, string, bool);

        Lexer lexer = new JustLexer("100000");
        AstNode node = primary.parse(lexer.tokens());

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstLeaf);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "100000");
    }
}