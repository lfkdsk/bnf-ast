package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;

import java.util.List;
import java.util.Queue;
import java.util.function.Function;

/**
 * Token 基类
 */
public abstract class AToken extends Element {

    //    private Factory factory;
    private Function<Token, ? extends AstLeaf> factory;

    public AToken(Function<Token, ? extends AstLeaf> factory) {
        if (factory == null) {
            this.factory = AstLeaf::new;
        }

        this.factory = factory;
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return tokenTest(lexer.peek());
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        Token token = lexer.poll();

        if (tokenTest(token)) {
            AstNode leaf = factory.apply(token);

            nodes.add(leaf);
        } else {
            throw new ParseException(token);
        }
    }

    /**
     * 判断是否符合该类Token
     * 标准的抽象方法
     *
     * @param token token
     * @return tof?
     */
    protected abstract boolean tokenTest(Token token);
}
