package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;

import java.util.function.Predicate;

public class AssertCapture<T extends AstNode> extends Capture<T> {

    private Predicate<T> predicate;

    public AssertCapture(BnfCom parser, Predicate<T> predicate) {
        super(parser);
        this.predicate = predicate;
    }

    @Override
    protected void capture(T node) {
        if (!predicate.test(node)) {
            throw new UnsupportedOperationException("Assert Capture Test Fail :"
                    + predicate.toString() + " with node :" + node.toString());
        }
    }
}
