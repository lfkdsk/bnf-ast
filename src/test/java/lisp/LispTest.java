package lisp;

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

import java.util.HashSet;
import java.util.Set;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

public class LispTest {

    @Test
    public void testLisp() {
        Set<String> reserved = new HashSet<>();
        reserved.add("`");
        reserved.add("(");
        reserved.add(")");

        // primary type
        BnfCom number = rule().number(NumberLiteral.class);
        BnfCom id = rule().identifier(IDLiteral.class, reserved);
        BnfCom string = rule().string(StringLiteral.class);
        BnfCom bool = rule().bool(BoolLiteral.class);

        // 原始类型
        BnfCom primary = wrapper().or(number, id, string, bool);
        // S 表达式 (func arg1 arg2 arg3)
        BnfCom expr0 = rule();
        // 符号引用
        BnfCom quote = rule().token("`").then(expr0);
        // S 表达式
        BnfCom expr = expr0.reset().or(primary, quote, rule().sep("(").repeat(expr0).sep(")"));
        // 整个程序
        BnfCom problem = rule().sep("(").repeat(expr).sep(")");

        Lexer lexer = new JustLexer("((lfkdsk \"lfkdsk\") `lfkdsk 100e100 true)");
        lexer.reserved("`");
        lexer.reserved("(");
        lexer.reserved(")");

        AstNode node = problem.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(4, node.childCount());
        Assert.assertEquals("true", node.child(3).toString());
    }
}
