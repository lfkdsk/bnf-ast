package lisp.literal;


import bnfgenast.ast.token.Token;

/**
 * String Literal =>
 * - "lfkdsk"
 * - "\"lfkdsk\""
 *
 * @author liufengkai
 * Created by liufengkai on 2017/7/18.
 */
public class StringLiteral extends Literal {

    public StringLiteral(Token token) {
        super(token);
    }

    @Override
    public String value() {
        return token.getText();
    }

//    @Override
//    public Object eval(JustContext env) {
//        if (!isNull(literal())) {
//            return literal();
//        }
//
//        return super.eval(env);
//    }
//
//    @Override
//    public String compile(JustContext env) {
//        return "(\"" + literal() + "\")";
//    }
}
