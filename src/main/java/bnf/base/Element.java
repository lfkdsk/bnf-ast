package bnf.base;


import ast.base.AstNode;
import exception.ParseException;
import lexer.Lexer;

import java.util.List;

public abstract class Element {
    /**
     * 语法分析
     *
     * @param lexer 语法分析器
     * @param nodes 节点
     * @throws ParseException
     */
    public abstract void parse(Lexer lexer, List<AstNode> nodes)
            throws ParseException;

    /**
     * 匹配
     *
     * @param lexer 语法分析器
     * @return tof?
     * @throws ParseException
     */
    public abstract boolean match(Lexer lexer) throws ParseException;


    @Override
    public boolean equals(Object obj) {
        String thisClassName = this.getClass().getSimpleName();
        String objClassName = obj.getClass().getSimpleName();

        return thisClassName.equals(objClassName);
    }
}