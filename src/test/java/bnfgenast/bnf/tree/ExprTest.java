package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.Operators;
import lexer.JustLexer;
import lisp.literal.NumberLiteral;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static bnfgenast.bnf.BnfCom.rule;
import static org.junit.Assert.*;

public class ExprTest {

    public static class ADD extends AstList {

        public ADD(List<AstNode> children) {
            super(children, 100);
        }
    }

    @Test
    public void testExpr() {
        BnfCom number = rule().number(NumberLiteral.class);
        Operators operators = new Operators();
        operators.add("+", 1, true, ADD.class);

        BnfCom expr = rule().expr(number, operators);
        JustLexer lexer = new JustLexer("1 + 1 + 1");

        AstNode node = expr.parse(lexer.tokens());
        // node = (1 + 1) + 1
        Assert.assertNotNull(node);
        Assert.assertEquals(3, node.childCount());
        Assert.assertTrue(node.child(0) instanceof AstList);
        Assert.assertEquals(3, node.child(0).childCount());
    }
}