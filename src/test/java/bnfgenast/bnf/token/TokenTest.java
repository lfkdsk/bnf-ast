package bnfgenast.bnf.token;

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
import static token.ReservedToken.reversed;

public class TokenTest {

    @Test
    public void testTokens() {
        BnfCom number = rule().number(NumberLiteral.class);
        BnfCom id = rule().identifier(IDLiteral.class, reversed());
        BnfCom string = rule().string(StringLiteral.class);
        BnfCom bool = rule().bool(BoolLiteral.class);

        BnfCom primary = wrapper().or(number, id, string, bool);

        Lexer lexer = new JustLexer("10000 \"lfkdsk\" lfkdsk true");
        AstNode numberNode = primary.parse(lexer.tokens());
        Assert.assertNotNull(numberNode);
        Assert.assertEquals(((AstLeaf) numberNode).token().getText(), "10000");

        AstNode stringNode = primary.parse(lexer.tokens());
        Assert.assertNotNull(stringNode);
        Assert.assertEquals(((AstLeaf) stringNode).token().getText(), "lfkdsk");

        AstNode idNode = primary.parse(lexer.tokens());
        Assert.assertNotNull(idNode);
        Assert.assertEquals(((AstLeaf) idNode).token().getText(), "lfkdsk");

        AstNode boolNode = primary.parse(lexer.tokens());
        Assert.assertNotNull(boolNode);
        Assert.assertEquals(((AstLeaf) boolNode).token().getText(), "true");
    }
}