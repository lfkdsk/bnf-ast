package lisp.literal;

import bnfgenast.ast.token.Token;
import bnfgenast.exception.ParseException;
import token.NumberToken;

/**
 * Number Literal.
 * We support two kinds of Number Value :
 * - Long Value
 * - Integer Value
 * - Float Value
 * - Double Value
 *
 * @author liufengkai
 * Created by liufengkai on 2017/7/24.
 */
public class NumberLiteral extends Literal {

    /**
     * Inner number
     */
    private Number number;

    public NumberLiteral(Token token) {
        super(token);

        if (token instanceof NumberToken) {
            this.number = ((NumberToken) token).getNumberValue();
        } else {
            throw new ParseException("UnSupport number name where token " +
                    "type is not NumberToken");
        }
    }

    @Override
    public Number value() {
        switch (token.getTag()) {
            case Token.INTEGER:
                return number.intValue();
            case Token.LONG:
                return number.longValue();
            case Token.FLOAT:
                return number.floatValue();
            default:
            case Token.DOUBLE:
                return number.doubleValue();
        }
    }

    @Override
    public String toString() {
        switch (token.getTag()) {
            case Token.INTEGER:
                return String.valueOf(number.intValue());
            case Token.LONG:
                return String.valueOf(number.longValue());
            case Token.FLOAT:
                return String.valueOf(number.floatValue());
            case Token.DOUBLE:
                return String.valueOf(number.doubleValue());
            default:
                return "undefine number literal";
        }
    }

    public NumberToken numberToken() {
        return (NumberToken) token;
    }

//    @Override
//    public Object eval(JustContext env) {
//        return castTokenValue(numberToken());
//    }
//
//    @Override
//    public String compile(JustContext env) {
//        switch (token.getTag()) {
//            case Token.LONG:
//                return number.longValue() + "L";
//            case Token.FLOAT:
//                return number.floatValue() + "F";
//            case Token.DOUBLE:
//                return number.doubleValue() + "D";
//        }
//
//        return super.compile(env);
//    }
}
