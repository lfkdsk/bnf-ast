/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ast.base;





import ast.token.Token;
import exception.ParseException;
import org.jetbrains.annotations.NotNull;
import utils.ObjectHelper;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * AST Leaf Node
 *
 * @author liufengkai
 */
public class AstLeaf extends AstNode {

    private static final ArrayList<AstNode> empty = new ArrayList<>();

    protected Token token;

    public AstLeaf(@NotNull Token token) {
        super(token.getTag());
        this.token = ObjectHelper.requireNonNull(token, "token could not be null");
    }

    public Token token() {
        return token;
    }

    @Override
    public AstNode child(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public Iterator<AstNode> children() {
        return empty.iterator();
    }

    @Override
    public int childCount() {
        return 0;
    }

    @Override
    public String location() {
        return "at line " + token.getLineNumber();
    }

    @Override
    public AstNode resetChild(int index, AstNode node) {
        throw new ParseException("Didn't support this method in Leaf");
    }

    @Override
    public String toString() {
        return token.getText();
    }
}
