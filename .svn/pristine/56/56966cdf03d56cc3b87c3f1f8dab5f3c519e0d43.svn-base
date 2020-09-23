package com.zlsoft.n1;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.n1
 * @ClassName: lightweightLock.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年06月28日 19:45:00
 */


/**
 * 执行过程：
 *   ### 1.主线程main启动，JVM分配一个栈内存
 *         -栈内存就是一个壳，里面是一个又一个栈帧组成
 *       2.主线程main调用main方法，产生一个栈帧A(main:29,stacks)
 *       3.主线程main继续执行方法method1，产生一个栈帧B(method1:32,stacks)
 *         -该栈帧里面存储包含了方法的局部变量表、操作数栈、返回地址、动态连接等信息
 *         -x存储在局部变量表中
 *       3.1.当执行到synchronized语句的时代码块的时候，在栈帧B中创建一个锁记录（Lock Record）的对象,内部可以存储
 *         -A.锁定对象的Mark Word
 *         -B.锁对象的对象引用（Object reference）指向锁对象，并尝试用 cas 替换 Object 的 Mark Word，将 Mark Word 的值存入锁记录
 *       4.继续执行method2，那么继续生成一个新的栈帧内存C（method2:20,stacks）,当执行到synchronized语句的时代码块的时候，在栈帧C中在创建一个锁记录（Lock Record）的对象、
 *         -A.锁定对象 NULL
 *         -B.锁对象的对象引用（Object reference）指向锁对象
 *      5.继续执行退出method2的synchronized语句的时候（执行完，解锁），判断该栈帧的里面的锁记录，如果锁记录为null,则表示有锁重入（对同一个对象多次加锁），则清除该锁记录。
 *      6.继续执行退出method1的synchronized语句的时候（执行完，解锁），判断该栈帧的里面的锁记录，如果锁记录不为null,则执行还原工作，把锁记录里面存储的markword执行cas还原到obj对象。
 *        成功：把00轻量级锁还原成01无锁状态，成功解锁
 *        失败：说明轻量级锁进行了锁膨胀或已经升级为重量级锁，进入重量级锁解锁流程
 */


/**
 *  Mark Word (64 bits)
 * 轻量级锁：-lightweight Lock：- 　线程锁记录地址（ptr_to_lock_record:62） + markword对象后两位 00
 */
public class lightweightLock {
    static final Object obj = new Object();
    //保护的共享成员变量
    static int counter = 0;
    public static void main(String[] args) {
        final int x = 5;
        method1(x);
    }
    public static void method1(Integer x) {
        synchronized( obj ) {
            // 同步块
            counter++;
            System.out.println(counter);
            System.out.println(x);
            method2();
        }
    }
    public static void method2() {
        synchronized( obj ) {
            // 同步块 B
            counter++;
            System.out.println(counter);
        }
    }
}