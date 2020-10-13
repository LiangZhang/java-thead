package com.zlsoft.juc.aqs.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.zlsoft.core.Sleeper.sleep;
import static java.lang.Thread.currentThread;

/**
 * ReentrantLock 特性2：可打断
 */
@Slf4j(topic = "c.TestInterrupt")
public class TestInterrupt {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        test2();
    }

    /**
     * 1.未打断锁，直接执行：
     */
    private static void test1() {
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                //如果没有竞争，次方法可以获取到lock对象锁
                //如果有竞争进入阻塞队列，可以被其他线程打断。
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //被打断，没有获得锁，不继续执行代码
                log.debug("等锁的过程中被打断");
                return;
            }
            //如果没有被打断，则执行后续代码
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        t1.start();
    }

    /**
     * 可打断锁：一个线程打断另一个线程的锁，防止死锁，无限制等待
     */
    private static void test2() {
        Thread t1 = new Thread(() -> {
            log.debug("线程{}启动...",currentThread().getName());
            try {
                //如果没有竞争，次方法可以获取到lock对象锁
                //如果有竞争进入阻塞队列，可以被其他线程打断,防止死锁，死等。
                lock.lockInterruptibly();
                //lock.lock() 不可打断，死等
                //lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //被打断，没有获得锁，不继续执行代码
                log.debug("线程{}等锁的过程中被打断,没有获取到锁",currentThread().getName());
                return;
            }
            //如果没有被打断，则执行后续代码
            try {
                log.debug("打断后线程{}获得了锁",currentThread().getName());
            } finally {
                lock.unlock();
            }
        }, "t1");


        //主线程先加锁：
        lock.lock();
        log.debug("主线程{}获得了锁",currentThread().getName());
        //t1进入阻塞队列等待:lock.lockInterruptibly();
        t1.start();
        sleep(1);
        log.debug("在主线程{}中，t1线程执行interrupt打断等待");
        t1.interrupt();
        log.debug("执行打断");
    }


    private static void test3() {
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            lock.lock();
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");


        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            sleep(1);
            t1.interrupt();
            log.debug("执行打断");
            sleep(1);
        } finally {
            log.debug("释放了锁");
            lock.unlock();
        }
    }





}
