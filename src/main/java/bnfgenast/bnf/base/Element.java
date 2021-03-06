package bnfgenast.bnf.base;


import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.exception.ParseException;
import bnfgenast.lexer.Lexer;

import java.util.List;
import java.util.Queue;

public abstract class Element {
    /**
     * 语法分析
     *
     * @param lexer 语法分析器
     * @param nodes 节点
     * @throws ParseException
     */
    public abstract void parse(Queue<Token> lexer, List<AstNode> nodes)
            throws ParseException;

    /**
     * 匹配
     *
     * @param lexer 语法分析器
     * @return tof?
     * @throws ParseException
     */
    public abstract boolean match(Queue<Token> lexer) throws ParseException;


    @Override
    public boolean equals(Object obj) {
        String thisClassName = this.getClass().getSimpleName();
        String objClassName = obj.getClass().getSimpleName();

        return thisClassName.equals(objClassName);
    }
}