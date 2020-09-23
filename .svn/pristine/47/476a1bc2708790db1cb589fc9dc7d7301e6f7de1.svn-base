package com.zlsoft.summarize;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.summarize
 * @ClassName: VolatileTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年08月14日 11:22:00
 */

/**
 * 希望 doInit() 方法仅被调用一次，下面的实现是否有问题，为什么?
 *  -不对，volatile只能保证有序性和可见性，适合
 *     A.一个线程写，一个线程读,保证可见性
 *     B.在double-checked locking，synchronized之外的代码禁止指令重排
 *  -这里在同一个线程里面，不能保证原子性，当多个线程的时候，doInit()会执行多次
 */
public class VolatileTest {
    static volatile boolean initialized;
    public static void main(String[] args) {
        test1();
    }
    private static void test1() {
        //正确代码
//        synchronized (VolatileTest.class){
//            if (initialized) {
//                return;
//            }
//        }

        if (initialized) {
            return;
        }
        doInit();
        initialized = true;
    }
    private static void doInit() {
    }
}
    
    
    