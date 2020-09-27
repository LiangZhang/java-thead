package com.zlsoft.juc.aqs.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * 锁超时等待：避免无限制等待
 */
@Slf4j(topic = "c.TestTimeout")
public class TestTimeout {
    public static void main(String[] args) {
        test4();
    }

    /**
     * 没有竞争情况：
     */
    private static void test1() {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            if (!lock.tryLock()) {
                log.debug("获取立刻失败，返回");
                return;
            }
            try {
                log.debug("获得了锁,继续执行临界区代码");
            } finally {
                lock.unlock();
            }
        }, "t1");

        t1.start();
    }

    /**
     * 防止无限制的等待：立即判断
     */
    private static void test2() {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("t1线程启动...");
            if (!lock.tryLock()) {
                log.debug("t1线程立即获取锁失败，返回");
                return;
            }
            try {
                log.debug("t1线程获得了锁,继续执行临界区代码");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("主线程先获得了锁");
        t1.start();
        try {
            sleep(2);
            log.debug("主线程继续执行...");
        } finally {
            lock.unlock();
        }
    }

    private static void test3() {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("t1线程启动...");
            try {
                if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                    log.debug("t1线程获取等待 1s 后失败，返回");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1线程获得了锁");
            lock.unlock();
        }, "t1");

        lock.lock();
        log.debug("主线程获得了锁");
        t1.start();

        sleep(2);
        log.debug("主线程2秒后释放锁");
        lock.unlock();

    }

    private static void test4() {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("t1线程启动...");
            try {
                if (!lock.tryLock(4, TimeUnit.SECONDS)) {
                    log.debug("t1线程获取等待 4s 后失败，返回");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1线程等待4秒后获得了锁");
            lock.unlock();
        }, "t1");

        lock.lock();
        log.debug("主线程获得了锁");
        t1.start();

        sleep(2);
        log.debug("主线程2秒后释放锁");
        lock.unlock();

    }

}
