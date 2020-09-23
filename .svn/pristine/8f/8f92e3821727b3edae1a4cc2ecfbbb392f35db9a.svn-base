package com.zlsoft.multiLock;

import com.zlsoft.core.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0.0
 * 锁粒度细分：多把锁
 *   -好处，是可以增强并发度
 *   -坏处，如果一个线程需要同时获得多把锁，就容易发生死锁
 */
public class multiLockTest {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(() -> {
            bigRoom.study();
        },"小南").start();
        new Thread(() -> {
            bigRoom.sleep();
        },"小女").start();
    }
}

@Slf4j(topic = "c.BigRoom")
class BigRoom {

    private final Object studyRoom = new Object();

    private final Object bedRoom = new Object();

    public void sleep() {
        synchronized (bedRoom) {
            log.debug("sleeping 2 小时");
            Sleeper.sleep(2);
        }
    }

    public void study() {
        synchronized (studyRoom) {
            log.debug("study 1 小时");
            Sleeper.sleep(1);
        }
    }

}