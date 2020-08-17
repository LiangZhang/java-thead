package com.zlsoft.doubleSingleton;

// 问题1：枚举单例是如何限制实例个数的
// 问题2：枚举单例在创建时是否有并发问题 -没有，静态成员变量

// 问题3：枚举单例能否被反射破坏单例 -不会
// 问题4：枚举单例能否被反序列化破坏单例
// 问题5：枚举单例属于懒汉式还是饿汉式 -饿汉式
// 问题6：枚举单例如果希望加入一些单例创建时的初始化逻辑该如何做 -用一个构造函数处理

/**
 *  枚举实现单例
 *   -枚举把属性编译成静态成员变量，在类加载时候创建，由JVM保证代码的线程安全性
 *  java文件反编译字节码：
 *     javac -encoding UTF-8 Singleton2.java 编译成class文件
 *     javap -c Singleton2.class
 */
public enum Singleton2 {
    //枚举的静态成员变量
    INSTANCE,
    TEST;
}
