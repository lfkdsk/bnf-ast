package ast.token;

import lombok.Getter;
import lombok.Setter;

/**
 * Basic Token && Token Tag
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/18.
 */
public class Token {

    public final static int EOF_TAG = -1, EOL_TAG = -2;

    public final static int LIST = 277;

    public final static int BOOLEAN = 293;

    public final static int STRING = 294;

    public final static int INTEGER = 295;

    public final static int LONG = 296;

    public final static int FLOAT = 297;

    public final static int DOUBLE = 298;


    /**
     * ID in Language (identity except keyword )
     * ep: lfkdsk ( and -> break is illegal )
     */
    public final static int ID = 299;

    /**
     * Constant / String Literal
     * ep: 12.31 / 1e-10 / "lfkdsk"
     */
    public final static int LITERAL = 300;

    /**
     * Key Word in Language
     * ep: &&
     */
    public final static int KEYWORD = 301;

    /**
     * & Symbol
     */
    public final static int AMPERSAND = 302;

    /**
     * && Symbol
     */
    public final static int AND = 303;

    /**
     * | Symbol
     */
    public final static int BAR = 304;

    /**
     * || Symbol
     */
    public final static int OR = 305;

    /**
     * < Symbol
     */
    public final static int LESS_THAN = 306;

    /**
     * <= Symbol
     */
    public final static int LESS_EQUAL_THAN = 307;

    public final static int GREAT_THAN = 308;

    public final static int GREAT_EQUAL_THAN = 309;

    /**
     * ? Symbol
     */
    public final static int QUESTION = 310;

    /**
     * : Symbol
     */
    public final static int COLON = 311;
    public final static int PLUS = 312;
    public final static int MINUS = 313;
    public final static int MULTIPLY = 314;
    public final static int DIVIDE = 315;
    public final static int MOD = 316;
    /**
     * !
     */
    public final static int EXCLAM = 317;
    /**
     * !=
     */
    public final static int NOT_EQUAL = 318;

    public final static int DOT = 319;

    public final static int COLLECT_GET_LEFT = 320;

    public final static int COLLECT_GET_RIGHT = 321;

    public final static int RESERVED = 322;

    public final static int EQUAL = 323;

    public final static int PLUS_OP = 324;

    public final static int OPERATOR = 325;

    public final static int COND = 326;

    /**
     * End of file
     */
    public static final Token EOF = new Token(-1, EOF_TAG);

    /**
     * End of line
     */
    public static final String EOL = "\\n";

    @Setter
    @Getter
    protected int lineNumber;

    protected int tag;

    public Token(int lineNumber, int tag) {
        this.lineNumber = lineNumber;
        this.tag = tag;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean isIdentifier() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public String getText() {
        return "";
    }

    public int getTag() {
        return tag;
    }

    public boolean isType() {
        return false;
    }

    public boolean isBool() {
        return false;
    }

    @Override
    public String toString() {
        return "Token{" +
                "lineNumber=" + lineNumber +
                ", tag=" + tag +
                '}';
    }
}
