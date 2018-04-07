package bnfgenast.bnf.token;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.base.Element;
import bnfgenast.bnf.base.Factory;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;
import java.util.Queue;

/**
 * Token 基类
 */
public abstract class AToken extends Element {

    protected Factory factory;

    public AToken(Class<? extends AstLeaf> clazz) {
        if (clazz == null) {
            clazz = AstLeaf.class;
        }

        factory = Factory.get(clazz, Token.class);
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return tokenTest(lexer.peek());
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        Token token = lexer.poll();

        if (tokenTest(token)) {
            AstNode leaf = factory.make(token);

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
