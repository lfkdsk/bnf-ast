package bnfgenast.bnf.capturer;

import bnfgenast.ast.base.AstNode;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public abstract class Capture<T extends AstNode> extends Element {

    private BnfCom parser;

    public Capture(BnfCom parser) {
        this.parser = parser;
    }

    @Override
    public void parse(Lexer lexer, List<AstNode> nodes) throws ParseException {
        T node = (T) parser.parse(lexer);
        capture(node);
        nodes.add(node);
    }

    @Override
    public boolean match(Lexer lexer) throws ParseException {
        return parser.match(lexer);
    }

    protected abstract void capture(T node);
}
