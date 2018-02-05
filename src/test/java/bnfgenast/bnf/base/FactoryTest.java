package bnfgenast.bnf.base;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FactoryTest {

    public static class AstFake2 extends AstList {


        public AstFake2(List<AstNode> children) {
            super(children, 10000);
        }

        public static AstFake2 create(List<AstNode> children) {
            return new AstFake2(children);
        }
    }

    @Test
    public void testMake() {
        BnfCom bnfCom = BnfCom.rule(AstFake2.class).token("lfkdsk");
        Lexer lexer = new JustLexer("lfkdsk");
        lexer.reserved("lfkdsk");
        AstNode node = bnfCom.parse(lexer);
        assertNotNull(node);
    }
}