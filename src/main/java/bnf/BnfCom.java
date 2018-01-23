package bnf;

import ast.base.AstLeaf;
import ast.base.AstNode;
import bnf.base.Element;
import bnf.base.Factory;
import bnf.base.Operators;
import bnf.leaf.Leaf;
import bnf.leaf.Skip;
import bnf.leaf.SkipOR;
import bnf.token.*;
import bnf.tree.*;
import exception.ParseException;
import lexer.Lexer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * BnfParser 巴克斯范式解析引擎
 *
 * @author liufengkai
 * @version 1.0
 * @since 2018.01.20
 */
public class BnfCom {

    /**
     * 存储全部的BNF表达式
     */
    protected List<Element> elements;

    protected Factory factory;

    @Getter
    private String ruleName;

    public BnfCom(Class<? extends AstNode> clazz) {
        reset(clazz);
    }

    protected BnfCom(BnfCom parser) {
        elements = parser.elements;
        factory = parser.factory;
    }

    /**
     * 分析处理
     *
     * @param lexer 词法分析
     * @return 节点
     * @throws ParseException
     */
    public AstNode parse(Lexer lexer) throws ParseException {
        ArrayList<AstNode> results = new ArrayList<>();
        for (Element e : elements) {
            e.parse(lexer, results);
        }
        return factory.make(results);
    }


    public boolean match(Lexer lexer) throws ParseException {
        if (elements.size() == 0) {
            return true;
        } else {
            Element e = elements.get(0);
            return e.match(lexer);
        }
    }

    /**
     * 初始化 / 新定义一个一条产生式
     *
     * @return Ast
     */
    public static BnfCom rule() {
        return rule(null);
    }

    /**
     * 初始化 / 新定义一个一条产生式
     *
     * @param clazz 类
     * @return Ast
     */
    public static BnfCom rule(Class<? extends AstNode> clazz) {
        return new BnfCom(clazz);
    }

    public BnfCom reset() {
        elements = new ArrayList<>();
        return this;
    }

    public BnfCom reset(Class<? extends AstNode> clazz) {
        elements = new ArrayList<>();
        factory = Factory.getForAstList(clazz);
        return this;
    }

    public BnfCom of(BnfCom origin) {
        this.elements = new ArrayList<>(origin.elements);
        this.factory = origin.factory;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 添加识别各种Token的方法
    ///////////////////////////////////////////////////////////////////////////

    public BnfCom number() {
        return number(null);
    }


    public BnfCom number(Class<? extends AstLeaf> clazz) {
        elements.add(new NumToken(clazz));
        return this;
    }

    public BnfCom identifier(Set<String> reserved) {
        return identifier(null, reserved);
    }

    public BnfCom identifier(Class<? extends AstLeaf> clazz,
                             Set<String> reserved) {
        elements.add(new IDToken(clazz, reserved));
        return this;
    }

    public BnfCom string() {
        return string(null);
    }

    public BnfCom string(Class<? extends AstLeaf> clazz) {
        elements.add(new StrToken(clazz));
        return this;
    }

    public BnfCom bool() {
        return bool(null);
    }

    public BnfCom bool(Class<? extends AstLeaf> clazz) {
        elements.add(new BoolToken(clazz));
        return this;
    }

    public BnfCom Null() {
        return Null(null);
    }

    public BnfCom Null(Class<? extends AstLeaf> clazz) {
        elements.add(new NullToken(clazz));
        return this;
    }

    /**
     * 添加非终结符
     *
     * @param pat
     * @return
     */
    public BnfCom token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

    /**
     * 插入符号
     *
     * @param pat 符号
     * @return 这种格式的符号(跳
     */
    public BnfCom sep(String... pat) {
        elements.add(new Skip(pat));
        return this;
    }

    /**
     * 插入一棵子树
     *
     * @param parser BNF
     * @return BNF
     */
    public BnfCom ast(BnfCom parser) {
        elements.add(new Tree(parser));
        return this;
    }

    /**
     * 多个对象传入or树
     *
     * @param parsers BNF
     * @return BNF
     */
    public BnfCom or(BnfCom... parsers) {
        elements.add(new OrTree(parsers));
        return this;
    }

    public BnfCom maybe(BnfCom parser) {
        BnfCom parser1 = new BnfCom(parser);

        parser1.reset();

        elements.add(new OrTree(new BnfCom[]{parser, parser1}));
        return this;
    }

    public BnfCom maybe(String token) {
        elements.add(new SkipOR(token));
        return this;
    }

    /**
     * onlyOne 只重复一次
     *
     * @param parser BNF
     * @return BNF
     */
    public BnfCom option(BnfCom parser) {
        elements.add(new Repeat(parser, true));
        return this;
    }

    /**
     * 重复多次的节点
     *
     * @param parser BNF
     * @return BNF
     */
    public BnfCom repeat(BnfCom parser) {
        elements.add(new Repeat(parser, false));
        return this;
    }

    public BnfCom expression(BnfCom subExp, Operators operators) {
        elements.add(new Expr(null, subExp, operators));
        return this;
    }

    public BnfCom expression(Class<? extends AstNode> clazz, BnfCom subExp,
                             Operators operators) {
        elements.add(new Expr(clazz, subExp, operators));
        return this;
    }

    public BnfCom prefix(BnfCom... parsers) {
        elements.add(new PrefixTree(parsers));
        return this;
    }

    public BnfCom insertChoice(BnfCom parser) {
        Element e = elements.get(0);
        if (e instanceof OrTree) {
            ((OrTree) e).insert(parser);
        } else {
            BnfCom otherWise = new BnfCom(this);
            reset(null);
            or(parser, otherWise);
        }
        return this;
    }

    public BnfCom literal(Class<? extends AstLeaf> strLiteral, String literal) {
        elements.add(new StableStringToken(strLiteral, literal));
        return this;
    }

    public BnfCom name(String name) {
        this.ruleName = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        BnfCom bnfCom = (BnfCom) obj;
        List<Element> objElements = bnfCom.elements;

        if (elements.size() != objElements.size()) {
            return false;
        }

        for (int i = 0; i < elements.size(); i++) {
            Element e1 = elements.get(i);
            Element e2 = objElements.get(i);

            if (!e1.equals(e2)) {
                return false;
            }
        }

        return true;
    }
}
