package bnfgenast.bnf;

import bnfgenast.ast.base.AstLeaf;
import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import bnfgenast.bnf.base.AstLeafCreator;
import bnfgenast.bnf.base.AstListCreator;
import bnfgenast.bnf.base.Element;
import bnfgenast.bnf.base.Operators;
import bnfgenast.bnf.capturer.CollectCapture;
import bnfgenast.bnf.capturer.PredicateCapture;
import bnfgenast.bnf.capturer.ConsumerCapture;
import bnfgenast.bnf.leaf.Leaf;
import bnfgenast.bnf.leaf.Skip;
import bnfgenast.bnf.leaf.SkipOR;
import bnfgenast.bnf.token.*;
import bnfgenast.bnf.tree.*;
import bnfgenast.exception.ParseException;

import java.util.Queue;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * BnfParser 巴克斯范式解析引擎
 *
 * @author liufengkai
 * @version 1.0
 * @since 2018.01.20
 */
public final class BnfCom<T extends AstNode> {

    /**
     * 存储全部的BNF表达式
     */
    private List<Element> elements;
    private AstListCreator<T> constructor;

    @Getter
    private String ruleName;

    protected BnfCom(BnfCom<T> parser) {
        this.elements = parser.elements;
        this.constructor = parser.constructor;
    }

    public BnfCom(AstListCreator<T> constructor) {
        this.constructor = constructor;
        this.elements = new ArrayList<>();
    }

    /**
     * 分析处理
     *
     * @param lexer 词法分析
     * @return 节点
     * @throws ParseException
     */
    public T parse(Queue<Token> lexer) throws ParseException {
        List<AstNode> results = new ArrayList<>();
        for (Element e : elements) {
            e.parse(lexer, results);
        }
        return constructor.apply(results);
    }


    public boolean match(Queue<Token> lexer) throws ParseException {
        if (elements.size() == 0) {
            return true;
        } else {
            Element e = elements.get(0);
            return e.match(lexer);
        }
    }

    private static AstListCreator<AstNode> fold = (children) -> {
        if (children != null && children.size() == 1) {
            return children.get(0);
        } else {
            return new AstList(children);
        }
    };

    /**
     * 初始化 / 新定义一个一条产生式
     *
     * @return Ast
     */
    public static BnfCom<AstNode> rule() {
        return rule(fold);
    }

    /**
     * 初始化 / 新定义一个一条产生式
     *
     * @param constructor class constructor
     * @return Ast
     */
    public static <E extends AstNode> BnfCom<E> rule(AstListCreator<E> constructor) {
        return new BnfCom<>(constructor);
    }

    /**
     * 产生一个 Wrapper 类，不会生成当前层，inner elements 只能有一个
     *
     * @return Wrapper's Inner Ast
     */
    public static BnfCom<AstNode> wrapper() {
        return wrapper(fold);
    }

    public static <E extends AstNode> BnfCom<E> wrapper(AstListCreator<E> constructor) {
        return rule(constructor);
    }

    public BnfCom<T> reset() {
        elements = new ArrayList<>();
        return this;
    }

    public BnfCom<T> of(BnfCom<T> origin) {
        this.elements = new ArrayList<>(origin.elements);
        this.constructor = origin.constructor;
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 添加识别各种Token的方法
    ///////////////////////////////////////////////////////////////////////////

    public BnfCom<T> number() {
        return number(AstLeaf::new);
    }

    public BnfCom<T> number(AstLeafCreator<? extends AstLeaf> factory) {
        elements.add(new NumToken(factory));
        return this;
    }

    public BnfCom<T> identifier(Set<String> reserved) {
        return identifier(AstLeaf::new, reserved);
    }

    public BnfCom<T> identifier(AstLeafCreator<? extends AstLeaf> factory,
                                Set<String> reserved) {
        elements.add(new IDToken(factory, reserved));
        return this;
    }

    public BnfCom<T> string() {
        return string(AstLeaf::new);
    }

    public BnfCom<T> string(AstLeafCreator<? extends AstLeaf> factory) {
        elements.add(new StrToken(factory));
        return this;
    }

    public BnfCom<T> bool() {
        return bool(AstLeaf::new);
    }

    public BnfCom<T> bool(Function<Token, ? extends AstLeaf> factory) {
        elements.add(new BoolToken(factory));
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Combinator 组合子
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 添加非终结符
     *
     * @param pat
     * @return
     */
    public BnfCom<T> token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

    /**
     * 插入符号
     *
     * @param pat 符号
     * @return 这种格式的符号(跳
     */
    public BnfCom<T> sep(String... pat) {
        elements.add(new Skip(pat));
        return this;
    }

    /**
     * 插入一棵子树
     *
     * @param parser BNF
     * @return BNF
     */
    public BnfCom<T> ast(BnfCom parser) {
        elements.add(new Tree(parser));
        return this;
    }

    /**
     * 多个对象传入or树
     *
     * @param parsers BNF
     * @return BNF
     */
    public BnfCom<T> or(BnfCom... parsers) {
        elements.add(new OrTree(parsers));
        return this;
    }

    public BnfCom<T> maybe(BnfCom parser) {
        BnfCom parser1 = new BnfCom(parser);

        parser1.reset();

        elements.add(new OrTree(new BnfCom[]{parser, parser1}));
        return this;
    }

    public BnfCom<T> maybe(String token) {
        elements.add(new SkipOR(token));
        return this;
    }

    /**
     * onlyOne 只重复一次
     *
     * @param parser BNF
     * @return BNF
     */
    public BnfCom<T> option(BnfCom parser) {
        elements.add(new Repeat(parser, true));
        return this;
    }

    /**
     * 重复多次的节点
     *
     * @param parser BNF
     * @return BNF
     */
    public BnfCom<T> repeat(BnfCom parser) {
        elements.add(new Repeat(parser, false));
        return this;
    }

    public BnfCom<T> expr(BnfCom subExp, Operators operators) {
        elements.add(new Expr<>(AstList::new, subExp, operators));
        return this;
    }

    public BnfCom<T> expr(AstListCreator<T> factory, BnfCom subExp,
                          Operators operators) {
        elements.add(new Expr<>(factory, subExp, operators));
        return this;
    }

    public BnfCom<T> prefix(BnfCom... parsers) {
        elements.add(new PrefixTree(parsers));
        return this;
    }

    public BnfCom<T> insertChoice(BnfCom parser) {
        Element e = elements.get(0);
        if (e instanceof OrTree) {
            ((OrTree) e).insert(parser);
        } else {
            BnfCom<T> otherWise = new BnfCom<>(this);
            reset();
            or(parser, otherWise);
        }
        return this;
    }

    public BnfCom<T> literal(Function<Token, ? extends AstLeaf> factory, String literal) {
        elements.add(new StableStringToken(factory, literal));
        return this;
    }

    public BnfCom<T> name(String name) {
        this.ruleName = name;
        return this;
    }

    public BnfCom<T> times(BnfCom parser, int times) {
        elements.add(new Times(parser, times));
        return this;
    }

    public BnfCom<T> most(BnfCom parser, int maxTimes) {
        elements.add(new Times(parser, Integer.MAX_VALUE, maxTimes, Integer.MAX_VALUE));
        return this;
    }

    public BnfCom<T> least(BnfCom parser, int minTimes) {
        elements.add(new Times(parser, Integer.MAX_VALUE, Integer.MAX_VALUE, minTimes));
        return this;
    }

    public BnfCom<T> range(BnfCom parser, int min, int max) {
        elements.add(new Times(parser, Integer.MAX_VALUE, max, min));
        return this;
    }

    public <E extends AstNode> BnfCom<T> consume(BnfCom bnfCom, Consumer<E> initial) {
        elements.add(new ConsumerCapture<>(bnfCom, initial));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <E extends AstNode> BnfCom<T> consume(Consumer<E> initial) {
        BnfCom<T> bnfCom = (BnfCom<T>) wrapper(fold);
        bnfCom.consume(this, initial);
        return bnfCom;
    }

    public <E extends AstNode> BnfCom<T> test(BnfCom bnfCom, Predicate<E> initial) {
        elements.add(new PredicateCapture<>(bnfCom, initial, true));
        return this;
    }

    public <E extends AstNode> BnfCom<T> not(BnfCom bnfCom, Predicate<E> initial) {
        elements.add(new PredicateCapture<>(bnfCom, initial, false));
        return this;
    }

    public <E extends AstNode> BnfCom<T> test(Predicate<E> initial) {
        BnfCom<T> bnfCom = wrapper(this.constructor);
        bnfCom.test(this, initial);
        return bnfCom;
    }

    public <E extends AstNode> BnfCom<T> not(Predicate<E> initial) {
        BnfCom<T> bnfCom = wrapper(this.constructor);
        bnfCom.not(this, initial);
        return bnfCom;
    }

    public <E extends AstNode> BnfCom<T> collect(BnfCom parser, List<E> collection) {
        elements.add(new CollectCapture<>(parser, collection));
        return this;
    }

    public <E extends AstNode> BnfCom<T> collect(List<E> collection) {
        BnfCom<T> bnfCom = wrapper(this.constructor);
        bnfCom.collect(this, collection);
        return bnfCom;
    }

    public BnfCom then(BnfCom parser) {
        return this.ast(parser);
    }

    @Override
    public boolean equals(Object obj) {
        BnfCom<T> bnfCom = (BnfCom<T>) obj;
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
