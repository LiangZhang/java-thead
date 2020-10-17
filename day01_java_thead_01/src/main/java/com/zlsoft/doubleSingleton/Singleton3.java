package com.zlsoft.doubleSingleton;

import java.io.Serializable;

/**
 * 分析问题：创建是单例模式的安全性
 *  饿汉式：类加载就会导致该单实例对象被创建
 *  懒汉式：类加载不会导致该单实例对象被创建，而是首次使用该对象时才会创建
 *  问题1：为什么加 final?
 *  答：防止继承类覆盖父类的方法
 *  问题2：如果实现了序列化接口, 还要做什么来防止反序列化破坏单例
 *  答：在反序列号化的时候，要生成一个新的对象，违背了单例模式的宗旨，，
 */
public final class Singleton3 implements Serializable {
    /**
     * 问题3：为什么设置为私有? 是否能防止反射创建新的实例?
     * 答：1.设置私有，防止其他类来创建这个对象
     *     2.不能，能通过反射暴力创建对象
     */
    private Singleton3() {}

    /**
     * 问题4：这样初始化是否能保证单例对象创建时的线程安全?
     * 答：能保证线程安全，静态成员变量初始化操作在类加载时候创建，由JVM保证代码的线程安全性
     */
    private static final Singleton3 INSTANCE = new Singleton3();

    /**
     * 问题5：为什么提供静态方法而不是直接将 INSTANCE 设置为 public, 说出你知道的理由
     * 答：
     *   a.提供封装性，提供懒惰的初始化，如果由public static final Singleton INSTANCE = new Singleton(),在类加载的时候创建。
     *   b.可以提供泛型支持之类
     *
     */
    public static Singleton3 getInstance() {
        return INSTANCE;
    }

    /**
     * 反序列化的时候防止破坏单例
     * @return
     */
    public Object readResolve() {
        return INSTANCE;
    }
}
