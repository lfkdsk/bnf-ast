package ast.base;


import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

/**
 * AST Tree Basic Node
 *
 * @author liufengkai
 */
public abstract class AstNode implements Iterable<AstNode> {

    /**
     * Spec Tag for Ast Node
     */
    @Getter
    private final int tag;

    /**
     * index of child
     */
    @Setter
    @Getter
    protected int childIndex = 0;

    /**
     * parent - node
     */
    @Setter
    @Getter
    protected AstNode parentNode;

    /**
     * hashCode => evalString
     */
    @Getter
    protected int hash = 0;

    @Getter
    @Setter
    private String target = "";


    public AstNode(int tag) {
        this.tag = tag;
    }

    /**
     * Get Spec Child AstNode
     *
     * @param index index number
     * @return Child Node
     */
    public abstract AstNode child(int index);

    /**
     * Return Iterator of Node
     *
     * @return Iterator
     */
    public abstract Iterator<AstNode> children();

    /**
     * 子节点数目
     *
     * @return count
     */
    public abstract int childCount();

    /**
     * pos
     *
     * @return location
     */
    public abstract String location();

    /**
     * replace child spec index
     *
     * @param index index num
     * @param node  replace node
     * @return node
     */
    public abstract AstNode resetChild(int index, AstNode node);

    /**
     * iterator of list
     *
     * @return list of node
     */
    @Override
    public Iterator<AstNode> iterator() {
        return children();
    }

    @Override
    public int hashCode() {
        String eval = toString();
        if (hash == 0 && eval.length() != 0) {
            hash = eval.hashCode();
        }

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }
}
