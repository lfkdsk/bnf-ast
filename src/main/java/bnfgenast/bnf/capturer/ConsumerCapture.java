package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;

import java.util.function.Consumer;

public class ConsumerCapture<T extends AstNode> extends Capture<T> {

    private Consumer<T> consumer;

    public ConsumerCapture(BnfCom parser, Consumer<T> consumer) {
        super(parser);
        this.consumer = consumer;
    }

    @Override
    protected void capture(T node) {
        consumer.accept(node);
    }
}
