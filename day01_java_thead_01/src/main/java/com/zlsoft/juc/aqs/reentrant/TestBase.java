package com.zlsoft.juc.aqs.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.juc.aqs.reentrant
 * @ClassName: TestBase.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 特性1：锁重入：ReentrantLock
 * @createTime 2020年07月27日 11:30:00
 */
@Slf4j(topic = "c.entrant")
public class TestBase {
    static ReentrantLock lock = new ReentrantLock();

    /**
     * 测试可重入特性：
     *   16:07:01.224 c.entrant [main] - execute method1
     *   16:07:01.227 c.entrant [main] - execute method2
     *   16:07:01.227 c.entrant [main] - execute method3
     *   1.可重入是指同一个线程如果首次获得了这把锁，那么因为它是这把锁的拥有者，因此有权利再次获取这把锁
     *   2.如果是不可重入锁，那么第二次获得锁时，自己也会被锁挡住
     * @param args
     */
    public static void main(String[] args) {
        method1();
    }
    public static void method1() {
        lock.lock();
        try {
            log.debug("execute method1");
            method2();
        } finally {
            lock.unlock();
        }
    }
    public static void method2() {
        lock.lock();
        try {
            log.debug("execute method2");
            method3();
        } finally {
            lock.unlock();
        }
    }
    public static void method3() {
        lock.lock();
        try {
            log.debug("execute method3");
        } finally {
            lock.unlock();
        }
    }
}