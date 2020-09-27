package com.zlsoft.juc.aqs;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.juc.aqs
 * @ClassName: AqsExplain.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月24日 09:34:00
 */


/**
 * 软考：
 * http://www.ruankao.org.cn/book
 *  Zh77993453
 *  AQS特点：
 *  所属：java.util.concurrent:java并发工具包
 *  AQS:全称是 AbstractQueuedSynchronizer，抽象的居于队列的同步器，是阻塞式锁和相关的同步器工具的框架
 *   - 框架：其他同步器都是基于他实现的
 *   - 锁：阻塞式的锁（乐观锁cas|悲观锁：Synchronizer）
 * 特点：
 *   a.用 state（private volatile int state;） 属性来表示资源的状态（分独占模式和共享模式），子类需要定义如何维护这个状态，控制如何获取
 *      -独占：一个线程访问资源
 *      -共享：运行多个线程访问一个资源，但是资源有一个上限
 *     锁和释放锁
 *      getState - 获取 state 状态
 *      setState - 设置 state 状态
 *      compareAndSetState - cas 机制设置 state 状态，不是加锁，保证赋值的原子性
 *  b.提供了基于 FIFO（先进先出 First Input First Output） 的等待队列，类似于 Monitor 的 EntryList
 *  c.条件变量(wait、notify)来实现等待、唤醒机制，支持多个条件变量，类似于 Monitor 的 WaitSet
 */

/**
 *  获取锁：
 *  // 如果获取锁失败
 * if (!tryAcquire(arg)) {
 *     // 入队, 可以选择阻塞当前线程 park unpark
 * }
 */

import com.zlsoft.core.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *  释放锁的姿势
 *  // 如果释放锁成功
 * if (tryRelease(arg)) {
 *     // 让阻塞线程恢复运行
 * }
 */
@Slf4j(topic = "zl.AqsExplain")
public class AqsExplain {
    public static void main(String[] args) {
        test2();
    }

    /**
     * 加自定义互斥锁：
     *  15:06:07.250 zl.AqsExplain [t1] - debug...
     *  15:06:08.254 zl.AqsExplain [t1] - unlock...
     *  15:06:08.254 zl.AqsExplain [t2] - debug...
     *  15:06:09.254 zl.AqsExplain [t2] - unlock...
     */
    private static void test1() {
        //自定义锁
        MyLock lock = new MyLock();

        new Thread(()->{
            lock.lock();
            try {
                log.debug("debug...");
                Sleeper.sleep(1);
            }
            finally {
                log.debug("unlock...");
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                log.debug("debug...");
                Sleeper.sleep(1);
            }
            finally {
                log.debug("unlock...");
                lock.unlock();
            }
        },"t2").start();
    }

    /**
     * 不可重入特性：
     *  15:10:03.434 zl.AqsExplain [t1] - debug...
     *  synchronized,ReentrantLock,都是可重入锁
     */
    private static void test2() {
        //自定义锁
        MyLock lock = new MyLock();
        new Thread(()->{
            lock.lock();
            log.debug("debug...");
            lock.lock();
            try {
                log.debug("debug...");
                Sleeper.sleep(1);
            }
            finally {
                log.debug("unlock...");
                lock.unlock();
            }
        },"t1").start();
    }
}

/**
 * 利用AQS实现一个自定义锁
 * 自定义锁：不可重入锁
 */
class MyLock implements Lock {

    private String age;

    /**
     * 同步器类：实现独占锁
     *  -Provides a framework for implementing blocking locks and related synchronizers (semaphores, events, etc) that rely on first-in-first-out (FIFO) wait queues.
     *  -提供一个框架，用于实现依赖于先进先出（FIFO）等待队列的阻塞锁和相关同步器（信号灯，事件等）
     */
    class MySync extends AbstractQueuedSynchronizer {
        /**
         * 尝试获取锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            //cas尝试加锁：保证原子性
            if(compareAndSetState(0,1)) {
                //设置当前线程为独占访问线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
            //return super.tryAcquire(arg);
        }

        /**
         * 尝试释放锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
//            return super.tryRelease(arg);
            //独占：不需要cas操作
            setExclusiveOwnerThread(null);
            //setExclusiveOwnerThread放在前面，state是volatile,他有写屏障，保证在之前的修改对其他线程可见
            setState(0);
            return true;
        }

        /**
         * 是否持有独占锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
//            return super.isHeldExclusively();
            return getState() == 1;
        }

        /**
         * 条件变量
         * @return
         */
        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();

    /**
     * 加锁：不成功，则进入等待队列等待
     */
    @Override
    public void lock() {
        //尝试加锁：如果不成功，放入等待队列
        /**
         * public final void acquire(int arg) {
         *     if (!tryAcquire(arg) &&
         *         acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
         *         selfInterrupt();
         * }
         */
        sync.acquire(1);
    }

    /**
     * 加锁：可以打断的，避免死锁，不像Synchronizer,不可打断.
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁：加锁一次，如果不成功，返回false
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        /**
         * public final boolean release(int arg) {
         *     if (tryRelease(arg)) {
         *         Node h = head;
         *         if (h != null && h.waitStatus != 0)
         *             unparkSuccessor(h);
         *         return true;
         *     }
         *     return false;
         * }
         */
        /**
         * release()
         *  1.owner thread = null
         *  2. state = 0
         *  3.唤醒正在阻塞的线程
         */
        sync.release(1);
    }

    /**
     * 创建条件变量
     * @return
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}

    
    
    