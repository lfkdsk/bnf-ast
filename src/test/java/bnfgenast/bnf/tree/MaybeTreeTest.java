package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class MaybeTreeTest {

    @Test
    public void testMaybe() {
        BnfCom literal = rule().literal(StringLiteral.class, "lfkdsk");
        BnfCom maybe = rule().token("lfkdsk").maybe(rule().sep("(").ast(literal).sep(")"));
        Lexer lexer = new JustLexer("lfkdsk");

        AstNode node = maybe.parse(lexer);
        Assert.assertNotNull(node);
        Assert.assertEquals(node.childCount(), 2);
        Assert.assertEquals(node.child(1).childCount(), 0);
        Assert.assertEquals(((AstLeaf) node.child(0)).token().getText(), "lfkdsk");

        lexer = new JustLexer("lfkdsk(lfkdsk)");
        node = maybe.parse(lexer);
        Assert.assertNotNull(node);
        Assert.assertEquals(node.childCount(), 2);
        Assert.assertEquals(((AstLeaf) node.child(1)).token().getText(), "lfkdsk");
    }
}
