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
public final class Singleton4 {
    private Singleton4() { }
    /**
     * inner class:内部静态类，对外不可见
     * 问题1：属于懒汉式还是饿汉式
     * 答：懒汉式
     */
    private static class LazyHolder {
        /**
         * 静态内部成员变量
         */
        static final Singleton4 INSTANCE = new Singleton4();
    }

    /**
     * 问题2：在创建时是否有并发问题
     * 答：能保证，类加载的时候，静态变量初始化由JVM保证线程安全性
     */
    public static Singleton4 getInstance() {
        return LazyHolder.INSTANCE;
    }
}