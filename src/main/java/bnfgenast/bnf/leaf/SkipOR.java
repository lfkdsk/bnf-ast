package bnfgenast.bnf.leaf;


import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class SkipOR extends Leaf {

    public SkipOR(String pat) {
        super(new String[]{pat});
    }

    @Override
    protected void find(List<AstNode> list, Token token) {

    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        String pat = tokens[0];
        Token token = lexer.peek();

        if (Objects.nonNull(token) && token.getText().equals(pat)) {
            lexer.poll();
        }
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return true;
    }
}
