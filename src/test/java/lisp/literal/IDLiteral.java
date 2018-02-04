
package lisp.literal;


import bnfgenast.ast.token.Token;

/**
 * ID Literal.
 * => The Language support's ID Literal
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/26.
 */
public class IDLiteral extends Literal {

    public IDLiteral(Token token) {
        super(token);
    }

    @Override
    public Object value() {
        return token.getText();
    }
}
