package com.zlsoft.juc.aqs.reentrant;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.juc.aqs.reentrant
 * @ClassName: TestReentrantReadWriteLock.java
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020年09月27日 09:52:00
 * @Description 测试读写锁
 *  -读锁不支持条件变量
 *  -重入时升级不支持：即持有读锁的情况下去获取写锁，会导致获取写锁永久等待
 *  -重入时降级支持：即持有写锁的情况下去获取读锁
 */

import com.zlsoft.core.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用场景：
 *  -当读操作远远高于写操作时，这时候使用 读写锁 让 （读-读） 可以并发，提高性能。 类似于数据库中的 select ...from ... lock in share mode
 *  - a.（读-读） 可以并发
 *  - b.（读-写） 互斥
 *  - c.（写-写） 互斥
 */
public class TestReentrantReadWriteLock {
    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    /**
     * 测试：（读-读）操作，检查是否互斥
     *  10:23:48.817 zl.DataContainer [t1] - 获取读锁...
     *  10:23:48.817 zl.DataContainer [t2] - 获取读锁...
     *  10:23:48.820 zl.DataContainer [t1] - 读操作...
     *  10:23:48.820 zl.DataContainer [t2] - 读操作...
     *  10:23:49.820 zl.DataContainer [t1] - 释放读锁...
     *  10:23:49.820 zl.DataContainer [t2] - 释放读锁...
     * 测试结果：不互斥
     */
    private static void test1() {
        DataContainer dataContainer = new DataContainer();
        new Thread(()->{
            dataContainer.read();
        },"t1").start();
        new Thread(()->{
            dataContainer.read();
        },"t2").start();
    }
    /**
     * 测试：（读-写）操作，检查是否互斥
     * 10:27:53.583 zl.DataContainer [t1] - 获取读锁...
     * 10:27:53.586 zl.DataContainer [t1] - 读操作...
     * 10:27:53.681 zl.DataContainer [t2] - 获取写锁...
     * 10:27:54.586 zl.DataContainer [t1] - 释放读锁...
     * 10:27:54.586 zl.DataContainer [t2] - 写操作...
     * 10:27:54.586 zl.DataContainer [t2] - 释放写锁...
     * 测试结果：互斥（写锁完成，释放，才能读），等了0.9秒，写操作才开始
     */
    private static void test2() throws InterruptedException {
        DataContainer dataContainer = new DataContainer();
        new Thread(()->{
            dataContainer.read();
        },"t1").start();
        Thread.sleep(100);
        new Thread(()->{
            dataContainer.write();
        },"t2").start();
    }

    /**
     * 测试：（写-写）操作，检查是否互斥
     * @author: L.Z QQ.191288065@qq.com
     * @createTime 2020-09-27 10:49:49
     *
     * 10:50:41.106 zl.DataContainer [t1] - 获取写锁...
     * 10:50:41.109 zl.DataContainer [t1] - 写操作...
     * 10:50:41.109 zl.DataContainer [t1] - 释放写锁...
     * 10:50:41.204 zl.DataContainer [t2] - 获取写锁...
     * 10:50:41.204 zl.DataContainer [t2] - 写操作...
     * 10:50:41.204 zl.DataContainer [t2] - 释放写锁...
     * 测试结果：互斥（写锁完成，释放，才能继续写），下次写操作才开始
    */
    private static void test3() throws InterruptedException {
        DataContainer dataContainer = new DataContainer();
        new Thread(()->{
            dataContainer.write();
        },"t1").start();
        Thread.sleep(100);
        new Thread(()->{
            dataContainer.write();
        },"t2").start();
    }
}

@Slf4j(topic = "zl.DataContainer")
class DataContainer {
    /**
     * 要保护的数据
     */
    private Object data;

    /**
     * 读写锁：
     */
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private ReentrantReadWriteLock.ReadLock  r = rw.readLock();
    /**
     * 写锁
     */
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();


    /**
     * 读操作用读锁去操作：
     * @return
     */
    public Object read() {
        //加读锁
        log.debug("获取读锁...");
        r.lock();
        try {
            log.debug("读操作...");
            //读取1秒，返回数据
            Sleeper.sleep(1);
            return  data;
        } finally {
            //释放读锁
            log.debug("释放读锁...");
            r.unlock();
        }

    }

    /**
     * 写操作：写锁
     */
    public void write() {
        //加读锁
        log.debug("获取写锁...");
        w.lock();
        try {
            log.debug("写操作...");
        } finally {
            //释放读锁
            log.debug("释放写锁...");
            w.unlock();
        }
    }
}
    
    
    