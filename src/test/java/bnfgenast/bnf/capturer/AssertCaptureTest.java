package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
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
        BnfCom leaf = wrapper().test(rule().token("lfkdsk"), node -> node instanceof AstLeaf);

        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");

        AstNode node = leaf.parse(lexer.tokens());
        Assert.assertNotNull(node);
    }

    @Test
    public void testAssertCaptureWithoutWrapper() {
        BnfCom leaf = rule().token("lfkdsk")
                            .test(node -> node instanceof AstNode)
                            .test(node -> node instanceof AstLeaf);

        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");

        AstNode node = leaf.parse(lexer.tokens());
        Assert.assertNotNull(node);
    }

    public static abstract class Literal extends AstLeaf {

        public Literal(Token token) {
            super(token);
        }

        public String name() {
            return token.getText();
        }

        public abstract Object value();
    }


    public static class StringLiteral extends Literal {

        public StringLiteral(Token token) {
            super(token);
        }

        @Override
        public String value() {
            return token.getText();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertCaptureString() {
        // literal is "lfkdsk" but not accept "lfkdsk"
        BnfCom string = rule().string(StringLiteral.class);

        BnfCom leaf = rule().ast(string)
                            .test(node -> node instanceof AstNode)
                            .test((Predicate<AstLeaf>) node -> {
                                System.out.println(node.token());
                                return !node.token().getText().equals("lfkdsk");
                            })
                            .test(node -> node instanceof AstLeaf);

        Lexer lexer = new JustLexer("\"lfkdsk\"");

        AstNode node = leaf.parse(lexer.tokens());
        Assert.assertNotNull(node);
    }
}