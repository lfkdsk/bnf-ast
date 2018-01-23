package bnf.leaf;


import ast.base.AstNode;
import ast.token.Token;
import exception.ParseException;
import lexer.Lexer;

import java.util.List;

public class SkipOR extends Leaf {

    public SkipOR(String pat) {
        super(new String[]{pat});
    }

    @Override
    protected void find(List<AstNode> list, Token token) {

    }

    @Override
    public void parse(Lexer lexer, List<AstNode> nodes) throws ParseException {
        String pat = tokens[0];
        Token token = lexer.peek(0);

        if (token.getText().equals(pat)) {
            lexer.read();
        }
    }

    @Override
    public boolean match(Lexer lexer) throws ParseException {
        return true;
    }
}
