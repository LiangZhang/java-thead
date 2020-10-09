package com.zlsoft.juc.aqs.stampedlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

import static com.zlsoft.core.Sleeper.sleep;


@Slf4j(topic = "c.TestStampedLock")
public class TestStampedLock {
    public static void main(String[] args) {
        test2();
    }

    /**
     * 读读并发
     *  -不会真正加读锁
     *  -乐观读，并不会加读锁
     *   10:18:20.822 c.DataContainerStamped [t1] - optimistic read locking...256
     *   10:18:21.328 c.DataContainerStamped [t2] - optimistic read locking...256
     *   10:18:21.328 c.DataContainerStamped [t2] - read finish...256, data:1
     *   10:18:21.825 c.DataContainerStamped [t1] - read finish...256, data:1
     */
    private static void test1() {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();
        //0.5秒在进行读
        sleep(0.5);
        new Thread(() -> {
            dataContainer.read(0);
        }, "t2").start();
    }

    /**
     * 读写并发
     *  10:24:00.531 c.DataContainerStamped [t1] - optimistic read locking...256
     *  10:24:01.031 c.DataContainerStamped [t2] - write lock 384
     *  - 验证戳失败
     *  10:24:01.536 c.DataContainerStamped [t1] - updating to read lock... 256
     *  - 锁升级
     *  10:24:03.031 c.DataContainerStamped [t2] - write unlock 384
     *  10:24:03.031 c.DataContainerStamped [t1] - read lock 513
     *  10:24:04.031 c.DataContainerStamped [t1] - read finish...513, data:0
     *  10:24:04.031 c.DataContainerStamped [t1] - read unlock 513
     */
    private static void test2() {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();
        //0.5秒在进行读
        sleep(0.5);
        new Thread(() -> {
            dataContainer.write(30);
        }, "t2").start();
    }
}

@Slf4j(topic = "c.DataContainerStamped")
class DataContainerStamped {
    /**
     * 共享数据：
     */
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }

    public int read(int readTime) {
        /**
         * 乐观读：获取一个戳
         */
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking...{}", stamp);
        sleep(readTime);
        if (lock.validate(stamp)) {
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        }
        // 锁升级 - 读锁，保证和其他锁互斥
        log.debug("updating to read lock... {}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            sleep(readTime);
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }

    /**
     * 写操作
     * @param newData
     */
    public void write(int newData) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            sleep(2);
            this.data = newData;
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}
