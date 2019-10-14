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

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

public class LispTest {

    @Test
    public void testLisp() {
        Lexer lexer = new JustLexer("((lfkdsk \"lfkdsk\") `lfkdsk 100e100 true)");
        lexer.reserved("`");
        lexer.reserved("(");
        lexer.reserved(")");

        // use
        BnfCom number = rule().number(NumberLiteral::new);
        BnfCom id = rule().identifier(IDLiteral::new, lexer.getReservedToken());
        BnfCom string = rule().string(StringLiteral::new);
        BnfCom bool = rule().bool(BoolLiteral::new);

        // number | id | string | bool
        BnfCom primary = wrapper().or(number, id, string, bool);
        BnfCom expr0 = rule();
        BnfCom quote = rule().token("`").then(expr0);
        BnfCom expr = expr0.reset().or(primary, quote, rule().sep("(").repeat(expr0).sep(")"));
        BnfCom problem = rule().sep("(").repeat(expr).sep(")");


        AstNode node = problem.parse(lexer.tokens());
        Assert.assertNotNull(node);
        Assert.assertEquals(4, node.childCount());
    }
}
