package com.zlsoft.reentrant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.reentrant
 * @ClassName: TestBase.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 锁重入：ReentrantLock
 * @createTime 2020年07月27日 11:30:00
 */
@Slf4j(topic = "c.entrant")
public class TestBase {
    //不可打断锁：
    static ReentrantLock lock = new ReentrantLock();
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