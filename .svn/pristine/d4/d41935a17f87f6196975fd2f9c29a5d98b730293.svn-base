package com.zlsoft.juc.aqs.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试非公平锁：IFIO
 */
public class TestFair {
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
     * 测试非公平锁
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " running...");
                } finally {
                    lock.unlock();
                }
            }, "t" + i).start();
        }

        // 1s 之后去争抢锁
        Thread.sleep(1000);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " running...");
                } finally {
                    lock.unlock();
                }
            }, "强行插入").start();
        }
        lock.unlock();
    }
}
