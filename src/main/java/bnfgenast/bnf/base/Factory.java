package bnfgenast.bnf.base;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class Factory {
    /**
     * 创建方法的方法名
     */
    private static final String factoryName = "create";

    protected abstract AstNode make0(Object arg) throws Exception;

    public AstNode make(Object arg) {
        try {
            return make0(arg);
        } catch (IllegalArgumentException e1) {
            throw e1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 直接创建一个AstList
     *
     * @param clazz 创建类
     * @return 工厂
     */
    public static Factory getForAstList(Class<? extends AstNode> clazz) {
        Factory f = get(clazz, List.class);

        if (f == null) {
            f = new Factory() {
                @Override
                protected AstNode make0(Object arg) throws Exception {
                    List<AstNode> results = (List<AstNode>) arg;
                    // 节点折叠
                    if (results.size() == 1) {
                        return results.get(0);
                    } else {
                        return new AstList(results, Token.LIST);
                    }
                }
            };
        }

        return f;
    }

    public static Factory getForWrapper(Factory factory) {
        return new Factory() {
            @Override
            protected AstNode make0(Object arg) throws Exception {
                List<AstNode> results = (List<AstNode>) arg;

                if (results.size() == 1) {
                    return results.get(0);
                } else if (results.size() > 1 && factory != null) {
                    return factory.make(arg);
                }

                throw new UnsupportedOperationException("Wrapper's child count will be 1 or wrapper more" + results.size());
            }
        };
    }

    /**
     * 静态构建工厂类
     *
     * @param clazz   类
     * @param argType 参数 也是一个类
     * @return 工厂
     */
    public static Factory get(Class<? extends AstNode> clazz,
                                 Class<?> argType) {
        if (clazz == null) {
            return null;
        }

        // 这是调用了对象的create函数
        try {
            final Method m = clazz.getMethod(factoryName, new Class<?>[]{argType});

            return new Factory() {
                @Override
                protected AstNode make0(Object arg) throws Exception {
                    return (AstNode) m.invoke(null, arg);
                }
            };

        } catch (NoSuchMethodException e) {
        }

        // 调用对象的构造
        try {
            final Constructor<? extends AstNode> c = clazz.getConstructor(argType);

            return new Factory() {
                @Override
                protected AstNode make0(Object arg) throws Exception {
                    return c.newInstance(arg);
                }
            };

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
