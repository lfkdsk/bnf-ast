package lexer;

/**
 * 匹配词素的默认正则表达式
 *
 * @author liufengkai
 */

public class JustRegex {
    /**
     * 匹配注释
     * 即注释线之后+任意数量的字符
     */
    public static final String annotation = "(//.*)";

    /**
     * 数字类型的正则
     */
    public static final String numberInt = "([0-9]+)";

    /**
     * 其余的全部数字类型
     */
    public static final String numberFloat = "(([0-9]+)(\\.[0-9]+)?([eE][-+]?[0-9]+)?)";

    /**
     * 变量名的正则
     */
    public static final String variable = "[A-Z_a-z][A-Z_a-z0-9]*";

    public static final String booleanReg = "==|<=|>=|!=|&&|\\|\\|";

    /**
     * 任意符号的正则匹配
     */
    public static final String symbol = "\\p{Punct}";

    public static final String bool = "true|false";

    /**
     * string 类型的正则
     * string 中是在两个双引号中的多个匹配模式
     * 支持 \" \n \\ 和任意一种不是"的符号匹配
     * 遇到\\"这种试图二度转译的应当会先与\\匹配
     * 使转译停止
     */
    public static final String string = "(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")";


    ///////////////////////////////////////////////////////////////////////////
    // Regex Define
    ///////////////////////////////////////////////////////////////////////////

    public static final String hobbyUnFormat = "\\s*((?<ANNOTATION>%1$s)|(?<FLOAT>%2$s)|(?<INT>%3$s)|(?<BOOL>%4$s)|(?<STRING>%5$s)|(?<TOKEN>%6$s)|(?<BOOLSYMBOL>%7$s)|(?<SYMBOL>%8$s))?";

    public static final String hobbyReg = String.format(
            hobbyUnFormat,
            annotation,
            numberFloat,
            numberInt,
            bool,
            string,
            variable,
            booleanReg,
            symbol
    );
}
