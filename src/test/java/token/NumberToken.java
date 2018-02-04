package token;


import bnfgenast.ast.token.Token;

/**
 * Number Token.
 * - Integer
 * - Long
 * - Float
 * - Double
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/24.
 */
public class NumberToken extends Token {

    /**
     * name => string
     * ep: 1000 => "1000" \ 111.22 => "111.22"
     */
    private String tokenString;

    /**
     * Inner Value
     */
    private Number numberValue;

    /**
     * Number Token
     *
     * @param lineNumber  location
     * @param tag         token.tag
     * @param tokenString name => string
     * @param value       number name
     */
    public NumberToken(int lineNumber, int tag,
                       String tokenString, Number value) {
        super(lineNumber, tag);
        this.tokenString = tokenString;
        this.numberValue = value;
    }

    public long longValue() {
        if (getTag() == Token.LONG) {
            return numberValue.longValue();
        }

        return 0;
//        throw new TransferNumberException("wrong name check " +
//                "| numberValue's Type isn't long Value " + numberValue.toString());
    }

    public int integerValue() {
        if (getTag() == Token.INTEGER) {
            return numberValue.intValue();
        }

        return 0;
//        throw new TransferNumberException("wrong name check " +
//                "| numberValue's Type isn't integer Value " + numberValue.toString());
    }

    public float floatValue() {
        if (getTag() == Token.FLOAT) {
            return numberValue.floatValue();
        }

        return 0;
//        throw new TransferNumberException("wrong name check " +
//                "| numberValue's Type isn't float Value " + numberValue.toString());
    }

    public double doubleValue() {
        if (getTag() == Token.DOUBLE) {
            return numberValue.doubleValue();
        }

        return 0;
//        throw new TransferNumberException("wrong name check " +
//                "| numberValue's Type isn't double Value " + numberValue.toString());
    }

    public Number getNumberValue() {
        return numberValue;
    }

    @Override
    public String getText() {
        return String.valueOf(getNumberValue());
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toString() {
        return "NumberToken{" +
                "tokenString='" + tokenString + '\'' +
                ", numberValue=" + numberValue +
                ", lineNumber=" + lineNumber +
                ", tag=" + tag +
                '}';
    }
}
