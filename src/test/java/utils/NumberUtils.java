package utils;


import bnfgenast.ast.token.Token;
import token.NumberToken;

/**
 * Parser Number.
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/24.
 */
public final class NumberUtils {

    private NumberUtils() {}

    public static Number parseLong(String s) {
        return parseNumber(Long.parseLong(s));
    }

    public static Number parseDouble(String s) {
        return parseNumber(Double.parseDouble(s));
    }

    /**
     * parser number: int / long
     *
     * @param l long number
     */
    public static Number parseNumber(long l) {
        int i = (int) l;
        if (i == l) {
            return i;
        }
        return l;
    }

    /**
     * parser number: float / double
     *
     * @param d double number
     */
    public static Number parseNumber(double d) {
        long f = (long) d;
        if (f == d) {
            return parseNumber(f);
        }
        return d;
    }

    private static int computeIntValue(Object v) {
        return v instanceof Integer ? (Integer) v : 0;
    }

    private static long computeLongValue(Object v) {
        return v instanceof Long ? (long) v : 0;
    }

    private static float computeFloatValue(Object v) {
        return v instanceof Float ? (Float) v : (int) 0;
    }

    private static double computeDoubleValue(Object v) {
        return v instanceof Double ? (double) v : 0;
    }

    public static Object computePlusValue(Number l, Number r) {
        return castTokenValue(computeValue(l) + computeValue(r),
                Math.max(numberValue(l), numberValue(r)));
    }

    public static Object computeMinusValue(Number l, Number r) {
        return castTokenValue(computeValue(l) - computeValue(r),
                Math.max(numberValue(l), numberValue(r)));
    }

    public static Object computeDivValue(Number l, Number r) {
        return castTokenValue(computeValue(l) / computeValue(r),
                Math.max(numberValue(l), numberValue(r)));
    }

    public static Object computeMulValue(Number l, Number r) {
        return castTokenValue(computeValue(l) * computeValue(r),
                Math.max(numberValue(l), numberValue(r)));
    }

    /**
     * Dirty Method to get the real Value of Number
     *
     * @param l numObj
     * @return Real Value
     */
    public static double computeValue(Object l) {
        return computeIntValue(l) + computeLongValue(l)
                + computeFloatValue(l) + computeDoubleValue(l);
    }

    public static Object computeNegative(Number l) {
        return castTokenValue(-computeValue(l), numberValue(l));
    }

    /**
     * Cast Token to origin Value
     *
     * @param number Num obj
     * @param flag   Type Tag
     * @return Number
     */
    public static Number castTokenValue(Number number, final int flag) {
        switch (flag) {
            case Token.INTEGER:
                return number.intValue();
            case Token.LONG:
                return number.longValue();
            case Token.FLOAT:
                return number.floatValue();
            case Token.DOUBLE:
            default:
                return number.doubleValue();
        }
    }

    /**
     * Cast Token to Value
     *
     * @param token Number Token
     * @return Number
     */
    public static Number castTokenValue(NumberToken token) {
        switch (token.getTag()) {
            case Token.INTEGER:
                return token.integerValue();
            case Token.LONG:
                return token.longValue();
            case Token.FLOAT:
                return token.floatValue();
            case Token.DOUBLE:
            default:
                return token.doubleValue();
        }
    }

    /**
     * Get Value in Number-Level Tree
     * int --> long --> float --> double
     *
     * @param obj number obj
     * @param <T> type extend Number
     * @return level tag
     */
    private static <T extends Number> int numberValue(T obj) {
        if (obj instanceof Integer) {
            return Token.INTEGER;
        } else if (obj instanceof Long) {
            return Token.LONG;
        } else if (obj instanceof Float) {
            return Token.FLOAT;
        } else {
            return Token.DOUBLE;
        }
    }

    /**
     * num1 & num2
     *
     * @param num1 n1
     * @param num2 n2
     * @return & literal
     */
    public static Object computeAmpersandValue(Number num1, Number num2) {
        return castTokenValue((long) computeValue(num1) & (long) computeValue(num2),
                Math.max(numberValue(num1), numberValue(num2)));
    }

    public static Object computeModValue(Number num1, Number num2) {
        return castTokenValue(computeValue(num1) % computeValue(num2),
                Math.max(numberValue(num1), numberValue(num2)));
    }
}
