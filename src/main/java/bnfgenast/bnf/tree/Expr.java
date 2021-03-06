package bnfgenast.bnf.tree;


import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.BnfCom;
import bnfgenast.bnf.base.*;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * 表达式子树
 */
public class Expr<T extends AstNode> extends Element {
    protected AstListCreator<T> factory;

    protected Operators ops;

    protected BnfCom factor;

    public Expr(AstListCreator<T> factory, BnfCom factor, Operators ops) {
        this.factory = factory;
        this.factor = factor;
        this.ops = ops;
    }

    @Override
    public void parse(Queue<Token> lexer, List<AstNode> nodes) throws ParseException {
        AstNode right = factor.parse(lexer);

        Precedence prec;

        while ((prec = nextOperator(lexer)) != null) {
            right = doShift(lexer, right, prec.value);
        }

        nodes.add(right);
    }

    private T doShift(Queue<Token> lexer, AstNode left, int prec) throws ParseException {
        List<AstNode> list = new ArrayList<>();

        list.add(left);
        // 读取一个符号
        list.add(new AstLeaf(lexer.poll()));
        // 返回节点放在右子树
        AstNode right = factor.parse(lexer);

        Precedence next;
        // 子树向右拓展
        while ((next = nextOperator(lexer)) != null && rightIsExpr(prec, next)) {
            right = doShift(lexer, right, next.value);
        }

        list.add(right);

        return factory.apply(list);
    }

    /**
     * 那取下一个符号
     *
     * @param lexer 词法
     * @return 符号
     * @throws ParseException
     */
    private Precedence nextOperator(Queue<Token> lexer) throws ParseException {
        Token token = lexer.peek();

        if (Objects.nonNull(token) && token.isIdentifier()) {
            // 从符号表里找对应的符号
            return ops.get(token.getText());
        } else {
            return null;
        }
    }

    /**
     * 比较和右侧符号的结合性
     *
     * @param prec     优先级
     * @param nextPrec 下一个符号的优先级
     * @return tof?
     */
    private static boolean rightIsExpr(int prec, Precedence nextPrec) {
        if (nextPrec.leftAssoc) {
            return prec > nextPrec.value;
        } else {
            return prec >= nextPrec.value;
        }
    }

    @Override
    public boolean match(Queue<Token> lexer) throws ParseException {
        return factor.match(lexer);
    }
}
