package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class OptionTreeTest {

    @Test
    public void testOption() {
        BnfCom literal = rule().literal(StringLiteral.class, "lfkdsk");
        BnfCom option = rule().token("lfkdsk").option(rule().sep("(").ast(literal).sep(")"));
        Lexer lexer = new JustLexer("lfkdsk");


        AstNode node = option.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstLeaf);

        lexer = new JustLexer("lfkdsk(lfkdsk)");
        node = option.parse(lexer.tokens());

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstList);
        Assert.assertEquals(2, node.childCount());
    }
}
