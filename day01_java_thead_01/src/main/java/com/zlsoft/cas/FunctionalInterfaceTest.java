package com.zlsoft.cas;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: FunctionalInterfaceTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 函数式编程可以用lambda表达式处理
 * @createTime 2020年08月26日 10:48:00
 */
public class FunctionalInterfaceTest {
    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        /**
         * Anonymous new Hello() can be replaced with lambda less...
         * (Ctrl+F1 Alt+T) Inspection info: Reports all anonymous classes which can be replaced with lambda expressions.
         * Note that if an anonymous class is converted into a stateless lambda, the same lambda object can be reused by Java runtime during subsequent invocations.
         * On the other hand, when an anonymous class is used, separate objects are created every time. Thus,
         * applying the quick-fix can cause the semantics change in rare cases,
         * e.g. when anonymous class instances are used as HashMap keys. Lambda syntax is not supported under Java 1.7 or earlier JVMs.
         */
        /**
         * 匿名新Hello（）可以替换为lambda less ...
         * （Ctrl + F1 Alt + T）
         * 检查信息：报告所有可以被lambda表达式替换的匿名类。
         * 请注意，如果将匿名类转换为无状态lambda，则Java运行时可以在后续调用期间重用同一lambda对象。
         * 另一方面，使用匿名类时，每次都会创建单独的对象。因此，在极少数情况下，例如，应用快速修复程序可能会导致语义变化。
         * 当匿名类实例用作HashMap键时。 Java 1.7或更早版本的JVM不支持Lambda语法。
         */
        Hello hello = new Hello() {
            @Override
            public String msg(String info) {
                return info + "World!";
            }
        };

        System.out.println("test functional:" + hello.msg("hello,"));
    }

    private static void test2() {
        Hello hello = param -> param + " World!";
        System.out.println("test functional:" + hello.msg("hello,"));
    }
}


@FunctionalInterface
interface Hello {
    String msg(String info);
}


    
    
    