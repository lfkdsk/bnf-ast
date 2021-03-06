package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;

import java.util.function.Predicate;

public class PredicateCapture<T extends AstNode> extends Capture<T> {

    private Predicate<T> predicate;

    private boolean testResult;

    public PredicateCapture(BnfCom parser, Predicate<T> predicate, boolean testResult) {
        super(parser);
        this.predicate = predicate;
        this.testResult = testResult;
    }

    @Override
    protected void capture(T node) {
        boolean result = predicate.test(node);
        if (testResult ^ result) {
            throw new IllegalArgumentException("Assert Capture Test Fail : result " + testResult + " "
                    + " with node :" + node.toString());
        }
    }
}
