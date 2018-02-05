
package token;


/**
 * Boolean Token
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/26.
 */
public class BoolToken extends ReservedToken {

    public enum BooleanEnum {
        TRUE("true"),
        FALSE("false");

        private final String booleanToken;

        BooleanEnum(String token) {
            this.booleanToken = token;
        }

        @Override
        public String toString() {
            return booleanToken;
        }
    }

    public BoolToken(int lineNumber, BooleanEnum booleanEnum) {
        super(lineNumber, booleanEnum.toString());
        this.tag = BOOLEAN;
    }

    /**
     * Judge token => Boolean Value
     *
     * @param token tokenString
     * @return BooleanEnum
     */
    public static BooleanEnum booleanValue(String token) {
        for (BooleanEnum booleanEnum : BooleanEnum.values()) {
            if (booleanEnum.booleanToken.equals(token)) {
                return booleanEnum;
            }
        }

        throw new IllegalArgumentException("use wrong boolean token " + token);
    }

    @Override
    public boolean isBool() {
        return true;
    }

    @Override
    public String toString() {
        return "BoolToken{" +
                "token='" + token + '\'' +
                ", lineNumber=" + lineNumber +
                ", tag=" + tag +
                '}';
    }
}
