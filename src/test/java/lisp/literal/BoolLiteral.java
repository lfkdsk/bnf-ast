
package lisp.literal;


import bnfgenast.ast.token.Token;

import static token.BoolToken.BooleanEnum.TRUE;
import static token.BoolToken.booleanValue;


/**
 * Boolean Literal => Support two Boolean Value.
 * - true
 * - false
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/26.
 */
public class BoolLiteral extends Literal {

    public BoolLiteral(Token token) {
        super(token);
    }

    @Override
    public Boolean value() {
        return booleanValue(token.getText()) == TRUE;
    }

//    @Override
//    public Object eval(JustContext env) {
//        return literal() ? Boolean.TRUE : Boolean.FALSE;
//    }
}
