package bnfgenast.bnf.tree;

import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.Element;
import bnfgenast.exception.ParseException;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrefixTree extends Element {
    protected BnfCom[] parsers;

    public PrefixTree(BnfCom[] parsers) {
        this.parsers = parsers;
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        final Queue<BnfCom> parsers = choose(lexer);
        final Queue<Token> recover = new ArrayDeque<>(lexer);

        while (parsers != null && !parsers.isEmpty()) {
            BnfCom parser = parsers.poll();
            AstNode node;

            try {
                node = parser.parse(lexer);
                nodes.add(node);
                break;
            } catch (ParseException e) {
                lexer.clear();
                lexer.addAll(recover);
            }
        }
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return choose(lexer) != null;
    }

    protected Queue<BnfCom> choose(Queue<Token> lexer) throws ParseException {
        Queue<BnfCom> chooses = new LinkedList<>();

        for (BnfCom parser : parsers) {
            if (parser.match(lexer)) {
                chooses.add(parser);
            }
        }

        return chooses.isEmpty() ? null : chooses;
    }
}