package exception;



import ast.token.Token;

import java.io.IOException;

/**
 * ParseException Parser Error
 *
 * @author liufengkai Created by liufengkai on 16/7/11.
 */
public class ParseException extends RuntimeException {

    public ParseException(Token token) {
        this("", token);
    }

    public ParseException(String msg, Token token) {
        super("syntax error around " + location(token) + " . " + msg);
    }

    /**
     * get location in file
     *
     * @param token com.lfkdsk.justel.token
     * @return return the description of com.lfkdsk.justel.exception
     */
    private static String location(Token token) {
        if (token == Token.EOF) {
            return " the last of line ";
        } else {
            return "\"" + token.getText() + "\" at line " + token.getLineNumber();
        }
    }

    public ParseException(IOException exc) {
        super(exc);
    }

    public ParseException(String msg) {
        super(msg);
    }
}
