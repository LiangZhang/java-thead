package com.zlsoft.deadlock;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.core.Sleeper.sleep;

/**
 *  使用 jps 定位进程 id，再用 jstack 定位死锁
 *  Found one Java-level deadlock:
 * =============================
 * "t2":
 *   waiting to lock monitor 0x000000001f2732a8 (object 0x000000076dd3d2c0, a java.lang.Object),
 *   which is held by "t1"
 * "t1":
 *   waiting to lock monitor 0x000000001f2747f8 (object 0x000000076dd3d2d0, a java.lang.Object),
 *   which is held by "t2"
 */

@Slf4j(topic = "c.TestDeadLock")
public class TestDeadLock {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Object A = new Object();
        Object B = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                log.debug("lock A");
                sleep(1);
                synchronized (B) {
                    log.debug("lock B");
                    log.debug("操作...");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                log.debug("lock B");
                sleep(0.5);
                synchronized (A) {
                    log.debug("lock A");
                    log.debug("操作...");
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}

