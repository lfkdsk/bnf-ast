package bnfgenast.bnf.leaf;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * 叶节点
 */
public class Leaf extends Element {
    protected String[] tokens;

    public Leaf(String[] pat) {
        this.tokens = pat;
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        Token token = lexer.poll();

        if (token.isIdentifier()) {
            for (String t : tokens) {
                if (t.equals(token.getText())) {
                    find(nodes, token);
                    return;
                }
            }
        }

        if (tokens.length > 0) {
            throw new ParseException(tokens[0] + " expected. ", token);
        } else {
            throw new ParseException(token);
        }
    }

    /**
     * 添加终结符
     *
     * @param list  list
     * @param token 终结符对应token
     */
    protected void find(List<AstNode> list, Token token) {
        list.add(new AstLeaf(token));
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        Token token = lexer.peek();

        if (Objects.nonNull(token) && token.isIdentifier()) {
            for (String t : tokens) {
                if (t.equals(token.getText())) {
                    return true;
                }
            }
        }

        return false;
    }
}
