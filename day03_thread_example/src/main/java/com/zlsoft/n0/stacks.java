package com.zlsoft.n0;
/**
 * Java Virtual Machine Stacks （Java 虚拟机栈）
 * 我们都知道 JVM 中由堆、栈、方法区所组成，其中栈内存是给谁用的呢？其实就是线程，每个线程启动后，虚拟
 * 机就会为其分配一块栈内存。
 * 每个栈由多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存
 * 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法
 */
public class stacks {
    //1.主线程main启动，JVM分配一个栈内存
    //  -栈内存就是一个壳，里面是一个又一个栈帧组成
    //2.主线程main调用main方法，产生一个栈帧(main:15,stacks)
    //3.继续执行方法method1，产生一个栈帧(method1:18,stacks)
    //  -该栈帧里面存储包含了方法的局部变量表、操作数栈、返回地址、动态连接等信息
    //  -x,y存储在局部变量表中
    //4.继续执行method2，那么继续生成一个新的栈帧内存（method2:20,stacks）
    public static void main(String[] args) {
        method1(10);
    }
    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }
    private static Object method2() {
        Object n = new Object();
        return n;
    }
}
