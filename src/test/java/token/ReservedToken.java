package token;


import bnfgenast.ast.token.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * Reserved Token
 * Language reserved word
 *
 * @author liufengkai
 * Created by liufengkai on 2017/7/26.
 * @see BoolToken
 */
public class ReservedToken extends Token {

    public static final String TRUE_TOKEN = "true",
            FALSE_TOKEN = "false";

    public static final String LP_TOKEN = "(", RP_TOKEN = ")";

    public static final String EQ_TOKEN = "==", UQ_TOKEN = "!=";

    public static final String LOGICAL_AND_TOKEN = "&&",
            LOGICAL_OR_TOKEN = "||", LOGICAL_F_TOKEN = "!";

    public static final String LT_TOKEN = "<", GT_TOKEN = ">";

    public static final String GTE_TOKEN = ">=", LTE_TOKEN = "<=";

    public static final String PLUS = "+", MINUS = "-",
            MUL = "*", DIV = "/", MOD = "%";

    public static final String COMMA = ",";

    public static final String DOT_TOKEN = ".";

    public static final String LM_TOKEN = "[", RM_TOKEN = "]";

    public static final String QUESTION_TOKEN = "?", COLON_TOKEN = ":",
            AMPERSAND_TOKEN = "&", BAR_TOKEN = "|";

    /**
     * reserved token str
     */
    protected String token;

    /**
     * reserved token in this set
     */
    private final static Set<String> reservedToken = new HashSet<>();

    static {
        // TRUE_TOKEN = "true", FALSE_TOKEN = "false"
        reservedToken.add(TRUE_TOKEN);
        reservedToken.add(FALSE_TOKEN);
        // LP_TOKEN = "(", RP_TOKEN = ")"
        reservedToken.add(LP_TOKEN);
        reservedToken.add(RP_TOKEN);
        // EQ_TOKEN = "==", UQ_TOKEN = "!="
        reservedToken.add(EQ_TOKEN);
        reservedToken.add(UQ_TOKEN);
        // LOGICAL_AND_TOKEN = "&&", LOGICAL_OR_TOKEN = "||", LOGICAL_F_TOKEN = "!"
        reservedToken.add(LOGICAL_AND_TOKEN);
        reservedToken.add(LOGICAL_OR_TOKEN);
        reservedToken.add(LOGICAL_F_TOKEN);
        // LT_TOKEN = "<", GT_TOKEN = ">"
        reservedToken.add(LT_TOKEN);
        reservedToken.add(GT_TOKEN);
        // PLUS = "+", MINUS = "-", MUL = "*", DIV = "/", MOD = "%"
        reservedToken.add(PLUS);
        reservedToken.add(MINUS);
        reservedToken.add(DIV);
        reservedToken.add(MOD);
        // COMMA = "," , DOT_TOKEN = ".", LM_TOKEN = "[", RM_TOKEN = "]"
        reservedToken.add(COMMA);
        reservedToken.add(DOT_TOKEN);
        reservedToken.add(LM_TOKEN);
        reservedToken.add(RM_TOKEN);
        // QUESTION_TOKEN = "?", COLON_TOKEN = ":",
        // AMPERSAND_TOKEN = "&", BAR_TOKEN = "|"
        reservedToken.add(QUESTION_TOKEN);
        reservedToken.add(COLON_TOKEN);
        reservedToken.add(AMPERSAND_TOKEN);
        reservedToken.add(BAR_TOKEN);
    }

    public static Set<String> reversed() {
        return new HashSet<>(reservedToken);
    }

    public ReservedToken(int lineNumber, String token) {
        super(lineNumber, Token.RESERVED);
        this.token = token;
    }

    @Override
    public String getText() {
        return token;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

}
