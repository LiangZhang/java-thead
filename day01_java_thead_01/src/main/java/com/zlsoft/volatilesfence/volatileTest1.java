package com.zlsoft.volatilesfence;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.volatilesfence
 * @ClassName: volatileTest1.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年08月14日 09:15:00
 */
public class volatileTest1 {

    static int x;
    static Object m = new Object();

    public static void main(String[] args) {
        test1();
    }

    /**
     * 线程解锁 m 之前对变量的写，对于接下来对 m 加锁的其它线程对该变量的读可见
     */
    private static void test1() {
        new Thread(() -> {
            synchronized (m) {
                System.out.println(x);
            }
        }, "t2").start();

        new Thread(() -> {
            synchronized (m) {
                x = 10;
            }
        }, "t1").start();
    }
}
    
    
    