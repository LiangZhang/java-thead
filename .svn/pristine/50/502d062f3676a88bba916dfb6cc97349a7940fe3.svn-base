package com.zlsoft.happensbefore;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.happensbefore
 * @ClassName: Happens.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description happens-before 规定了对共享变量的写操作对其它线程的读操作可见，它是可见性与有序性的一套规则总结，抛
 *                             开以下 happens-before 规则，JMM 并不能保证一个线程对共享变量的写，对于其它线程对该共享变量的读可见
 * @createTime 2020年08月14日 09:27:00
 */
@Slf4j(topic = "c.happensBefore")
public class Happens {
    static int x;
    static Object m = new Object();
    static volatile int y;
    static int z;

    static int a;
    static volatile int b;

    public static void main(String[] args) {
        test4();
    }

    /**
     * 1.synchronized:线程解锁 m 之前对变量的写，对于接下来对 m 加锁的其它线程对该变量的读可见
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

    /**
     * 2.volatile 线程对 volatile 变量的写，对接下来其它线程对该变量的读可见
     */
    private static void test2(){
        new Thread(()->{
            y = 10;
        },"t1").start();
        new Thread(()->{
            System.out.println(y);
        },"t2").start();
    }

    /**
     * 3.线程 t1 打断 t2（interrupt）前对变量的写，对于其他线程得知 t2 被打断后对变量的读可见（通过
     * t2.interrupted 或 t2.isInterrupted）
     */
    private static void test3() {
        Thread t2 = new Thread(()->{
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    log.debug("t2线程{}获取变量Z值：{}",Thread.currentThread().getName(),z);
                    System.out.println(z);
                    break;
                }
            }
        },"t2");
        t2.start();
        new Thread(()->{
            //睡眠一秒打断t2线程
            sleep(1);
            z = 10;
            t2.interrupt();
        },"t1").start();
        while(!t2.isInterrupted()) {
            Thread.yield();
        }
        log.debug("线程{}获取变量Z值：{}",Thread.currentThread().getName(),z);
        System.out.println(z);
    }

    /**
     * 4.volatile在多线程中对读写屏障对共享变量的同步
     */
    private static void test4(){
        new Thread(()->{
            //**写屏障（sfence）保证在该屏障之前的，对共享变量的改动，都同步到主存当中
            a = 10;
            //写屏障的时候，对共享变量同步到主存中，这里a=10同步到主存中
            b = 20;
        },"t1").start();
        new Thread(()->{
            //**读屏障（lfence）保证在该屏障之后，对共享变量的读取，加载的是主存中最新数据
            sleep(1);
            log.debug("b对t2可见, 同时a=10由于读屏障也对t2可见：a:{}",a);
//            log.debug("b对t2可见, 同时a=10由于读屏障也对t2可见：a:{},b:{}",a,b);
//            log.debug("b对t2可见, 同时a=10由于读屏障也对t2可见：b:{},a:{}",b,a);
        },"t2").start();
    }
}
    
    
    