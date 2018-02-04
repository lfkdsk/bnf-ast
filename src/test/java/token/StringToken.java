package token;


import bnfgenast.ast.token.Token;

/**
 * String Token.
 * used in lexer => will be add to spec Literal.
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/24.
 */
public class StringToken extends Token implements Comparable<StringToken> {
    private String text;

    public StringToken(int lineNumber, String text) {
        super(lineNumber, Token.STRING);
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String toString() {
        return "StringToken{" +
                "text='" + text + '\'' +
                ", lineNumber=" + lineNumber +
                ", tag=" + tag +
                '}';
    }

    @Override
    public int compareTo(StringToken o) {
        return getText().compareTo(o.getText());
    }
}
