package com.zlsoft.summarize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 规定次序执行线程代码：
 */
@Slf4j(topic = "c.WaitNotify")
public class WaitNotify {
    static final Object lock = new Object();
    // 表示 t2 是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        log.debug("t1线程先睡眠5秒，在释放Lock锁，让其他线程可以获得lock锁");
                        TimeUnit.SECONDS.sleep(5);
                        log.debug("t1线程醒来，缩放Lock锁,进入waitset休息室，等待唤醒。");
                        //lock。wait()等待后，释放lock锁，t2线程可以获得lock锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");
        /**
         *  如果t2线程先执行，则lock.notify();唤醒为null，没有线程在Lock的waitset中等待，那么t1线程直接打印1
         *  如果t1线程先执行，则在t1线程的lock.wait()d的时候，缩放锁，t2线程可以获得锁，执行代码
         */
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("获得{}锁，开始执行代码",lock);
                log.debug("2");
                t2runned = true;
                log.debug("唤醒在对象锁{}waitset休息室等待的线程",lock);
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
