package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstList;
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

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

public class CaptureTest {

    public static class AstFake extends AstList {

        public String success = null;

        public AstFake(List<AstNode> children) {
            super(children, 100);
        }

        public void setSuccess(String success) {
            this.success = success;
        }
    }

    @Test
    public void testCaptureTest() {
        BnfCom leaf = wrapper().consume(rule(AstFake.class).token("lfkdsk"), (Consumer<AstFake>) astNode -> astNode.setSuccess("ok"));

        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");

        AstNode node = leaf.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstFake);
        Assert.assertEquals(((AstFake) node).success, "ok");
    }

    @Test
    public void testCaptureWithoutWrapper() {
        BnfCom leaf = rule(AstFake.class).token("lfkdsk")
                                         .consume(node -> System.out.println("print 1"))
                                         .consume(node -> System.out.println("print 2"))
                                         .consume((Consumer<AstFake>) astNode -> astNode.setSuccess("ok"));

        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");

        AstNode node = leaf.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstFake);
        Assert.assertEquals(((AstFake) node).success, "ok");
    }

    @Test
    public void testComplexCaptureTest() {
        BnfCom leaf = rule().token("lfkdsk")
                            .consume(node -> System.out.println("print 1"))
                            .consume(node -> System.out.println("print 2"))
                            .token("[")
                            .consume((Consumer<AstList>) node -> node.getChildren().add(new AstFake(Collections.emptyList())));

        Lexer lexer = new JustLexer("lfkdsk [");
        lexer.reserved("lfkdsk");
        lexer.reserved("[");

        AstNode node = leaf.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstList);
        Assert.assertEquals(node.childCount(), 3);
    }

    @Test
    public void testComplexCaptureTest1() {
        Lexer lexer = new JustLexer("10000");
        lexer.reserved("`");
        lexer.reserved("(");
        lexer.reserved(")");

        // use
        BnfCom number = rule().number(NumberLiteral.class);
        BnfCom id = rule().identifier(IDLiteral.class, lexer.getReservedToken());
        BnfCom string = rule().string(StringLiteral.class);
        BnfCom bool = rule().bool(BoolLiteral.class);

        // number | id | string | bool
        BnfCom primary = wrapper().or(number, id, string, bool).consume(astNode -> System.out.println(astNode.toString()));

        primary.parse(lexer);
    }
}