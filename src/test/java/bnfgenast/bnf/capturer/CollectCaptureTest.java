package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import lexer.JustLexer;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static bnfgenast.bnf.BnfCom.rule;

public class CollectCaptureTest {

    @Test
    public void testCollectNodes() {
        List<AstNode> leaves = new LinkedList<>();
        BnfCom times = rule().token("lfkdsk").collect(leaves).times(
                rule().sep(",").token("lfkdsk").collect(leaves),
                3
        );

        Lexer lexer1 = new JustLexer("lfkdsk,lfkdsk,lfkdsk");
        AstNode node = times.parse(lexer1);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstList);
        Assert.assertEquals(node.childCount(), 3);
        Assert.assertEquals(leaves.size(), 3);
    }
}