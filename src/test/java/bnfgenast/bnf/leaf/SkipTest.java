package bnfgenast.bnf.leaf;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import lisp.literal.IDLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;

public class SkipTest {

    @Test
    public void testSkip() {
        Lexer lexer = new JustLexer("function name()");

        BnfCom id = rule().identifier(IDLiteral.class, lexer.getReservedToken());
        BnfCom token = rule().sep("function").then(id).sep("(").sep(")");


        AstNode node = token.parse(lexer);
        Assert.assertNotNull(node);
        Assert.assertEquals(((AstLeaf) node).token().getText(), "name");
    }
}