package bnfgenast.bnf.base;


import bnfgenast.ast.base.AstNode;

import java.util.HashMap;

/**
 * Operator Node
 */
public class Operators extends HashMap<String, Precedence> {
    // 结合性
    public static boolean LEFT = true;

    public static boolean RIGHT = false;

    /**
     * add Operators
     *
     * @param name      Token String
     * @param pres      Priority
     * @param leftAssoc left or right
     * @param clazz     create node class file
     */
    public void add(String name, int pres,
                    boolean leftAssoc, Class<? extends AstNode> clazz) {
        put(name, new Precedence(pres, leftAssoc, clazz));
    }
}
