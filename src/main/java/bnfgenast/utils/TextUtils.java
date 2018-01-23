package bnfgenast.utils;

public class TextUtils {
    private TextUtils() { /* cannot be instantiated */ }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            }
            for (int i = 0; i < length; i++) {
                if (a.charAt(i) != b.charAt(i)) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns list of multiple {@link CharSequence} joined into a single
     * {@link CharSequence} separated by localized delimiter such as ", ".
     *
     * @hide
     */
    public static CharSequence join(Iterable<CharSequence> list) {
        final CharSequence delimiter = ", ";
        return join(delimiter, list);
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Iterable<?> tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * 所谓字符串转译
     *
     * @param str 传入字符串
     * @return 返回字符串
     */
    public static String toStringLiteral(String str) {
        StringBuilder builder = new StringBuilder();

        int length = str.length() - 1;

        for (int i = 1; i < length; i++) {
            char ch = str.charAt(i);

            // 发现需要转译的\
            if (ch == '\\' && i + 1 < length) {
                // 取下一个字符
                int ch2 = str.charAt(i + 1);
                // 手动跳过
                if (ch2 == '"' || ch2 == '\\') {
                    ch = str.charAt(++i);
                    // 手工转译嵌入\n
                } else if (ch2 == 'n') {
                    ++i;
                    ch = '\n';
                }
            }

            builder.append(ch);
        }

        return builder.toString();
    }

    /**
     * String Wrapper
     *
     * @param wrapper inner-str
     * @return \"wrapper\"
     */
    public static String w(String wrapper) {
        return String.format("\"%s\"", wrapper);
    }
}
