package token;


import bnfgenast.ast.token.Token;

/**
 * ID Token.
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/24.
 */
public class IDToken extends Token {

    /**
     * Token String.
     */
    private String text;

    /**
     * ID Token
     *
     * @param lineNumber local number.
     * @param text       text token
     */
    public IDToken(int lineNumber, String text) {
        super(lineNumber, Token.ID);
        this.text = text;

        if (text.equals(Token.EOL)) {
            this.tag = Token.EOL_TAG;
        }
    }

    public boolean isIdentifier() {
        return true;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "IDToken{" +
                "text='" + text + '\'' +
                ", lineNumber=" + lineNumber +
                ", tag=" + tag +
                '}';
    }
}
