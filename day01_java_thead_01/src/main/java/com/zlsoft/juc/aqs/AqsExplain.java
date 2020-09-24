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

/**
 *  释放锁的姿势
 *  // 如果释放锁成功
 * if (tryRelease(arg)) {
 *     // 让阻塞线程恢复运行
 * }
 */
public class AqsExplain {
}
    
    
    