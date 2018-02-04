package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CollectCapture<T extends AstNode> extends Capture<T> {

    private List<T> nodes;

    public CollectCapture(@NotNull BnfCom parser,
                          @NotNull List<T> nodeSet) {
        super(parser);
        this.nodes = nodeSet;
    }

    @Override
    protected void capture(T node) {
        nodes.add(node);
    }
}
