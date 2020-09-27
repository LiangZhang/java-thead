package com.zlsoft.juc.aqs.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.zlsoft.core.Sleeper.sleep;

@Slf4j(topic = "c.TestCondition")
public class TestCondition {

    static ReentrantLock lock = new ReentrantLock();
    static Condition waitCigaretteWaitSet = lock.newCondition();
    static Condition waitbreakfastWaitSet = lock.newCondition();
    static volatile boolean hasCigrette = false;
    static volatile boolean hasBreakfast = false;

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("有烟没有？[{}]",hasCigrette);
                //while循环防止虚假唤醒：
                while (!hasCigrette) {
                    log.debug("没有烟，歇会儿！");
                    try {
                        //进入一个休息室等待，await 执行后，会释放锁lock，进入 conditionObject 等待
                        waitCigaretteWaitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟了，[{}],可以开始干活了",hasCigrette);
            } finally {
                log.info("送烟的线程结束，解锁");
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                while (!hasBreakfast) {
                    try {
                        waitbreakfastWaitSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("等到了它的早餐");
            } finally {
                log.info("送早餐的线程");
                lock.unlock();
            }
        }).start();

        sleep(1);
        sendBreakfast();
        sleep(1);
        sendCigarette();
    }

    /**
     *
     */
    private static void test1() {

    }

    private static void sendCigarette() {
        lock.lock();
        try {
            log.debug("送烟来了");
            hasCigrette = true;
            waitCigaretteWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    private static void sendBreakfast() {
        lock.lock();
        try {
            log.debug("送早餐来了");
            hasBreakfast = true;
            waitbreakfastWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }
}
