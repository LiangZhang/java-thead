package com.zlsoft.wait;

import com.zlsoft.core.Sleeper;
import com.zlsoft.core.TranslationClassLayout;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.wait
 * @ClassName: notify.java
 *  wait/notity、notifyAll操作的是对象上的线程
 *   -obj.wait() 让进入 object 监视器的线程到 waitSet 等待
 *   -obj.notify() 在 object 上正在 waitSet 等待的线程中挑一个唤醒
 *   -obj.notifyAll() 让 object 上正在 waitSet 等待的线程全部唤醒
 *   -当调用他们后，obj加的重量级锁
 *  它们都是线程之间进行协作的手段，都属于 Object 对象的方法。必须获得此对象的锁，才能调用这几个方法
 *  区别：
 *    1) sleep 是 Thread 方法，而 wait 是 Object 的方法
 *    2) sleep 不需要强制和 synchronized 配合使用，但 wait 需要和 synchronized 一起用
 *    3) sleep 在睡眠的同时，不会释放对象锁的，但 wait 在等待的时候会释放对象锁
 *    4) 它们状态 TIMED_WAITING
 */
@Slf4j(topic = "c.notify")
public class notify {
    //final 锁对象的引用不会变，锁定的同一个对象
    static final Object lock = new Object();
    static final Object lockOthers = new Object();

    public static void main(String[] args) throws InterruptedException {
        test6();
    }


    /**
     * wait:对象必须获得锁才能使用
     */
    public static void test1() {
        try {
            //Exception in thread "main" java.lang.IllegalMonitorStateException
            //lock对象都没有获得锁，不能加入WaitSet中去，报异常 java.lang.IllegalMonitorStateException
            log.info(new TranslationClassLayout(lock).toMarkWord());
            lock.wait();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * notify唤醒wait线程
     * @throws InterruptedException
     */
    public static void test2() throws InterruptedException {
        log.info("主线程加锁前：" + new TranslationClassLayout(lock).toMarkWord());
        synchronized (lock) {
            log.info("主线程加锁中：" + new TranslationClassLayout(lock).toMarkWord());
        }
        log.info("主线程解锁：" + new TranslationClassLayout(lock).toMarkWord());
        new Thread(() -> {
            synchronized (lock) {
                log.debug("t1 执行....");
                log.info("t1线程加锁中：" + new TranslationClassLayout(lock).toMarkWord());
                try {
                    //释放对象上的锁,让该线程进入waitset，等待该对象唤醒线程继续执行代码
                    lock.wait(); // 让线程t1在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("唤醒t1：" + new TranslationClassLayout(lock).toMarkWord());
                log.debug("唤醒t1执行其它代码....");
            }
        },"t1").start();
        new Thread(() -> {
            synchronized (lock) {
                log.debug("t2执行....");
                log.info("t2线程加锁中：" + new TranslationClassLayout(lock).toMarkWord());
                try {
                    lock.wait(); // 让线程t2在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("唤醒t2：" + new TranslationClassLayout(lock).toMarkWord());
                log.debug("唤醒t2其它代码....");
            }
        },"t2").start();
        log.info("主线程：" + new TranslationClassLayout(lock).toMarkWord());
        // 主线程两秒后执行
        TimeUnit.SECONDS.sleep(5);
        log.info("主线程：" + new TranslationClassLayout(lock).toMarkWord());
        //主线程获取到锁：t1、t2进入了WaitSet
        synchronized (lock) {
//            lock.notify(); // 唤醒obj上一个线程
            lock.notifyAll(); // 唤醒obj上所有等待线程
        }

    }


    /**
     * sleep、wait区别：
     * @throws InterruptedException
     */
    public static void test3() throws InterruptedException {
        log.info("主线程开始执行业务逻辑：");
        new Thread(() -> {
            synchronized (lock) {
                log.debug("lock对象在t1线程获得对象锁");
                try {
//                 ==》如果线程调用sleep:
//                 线程调用sleep睡眠的时候，放弃CPU是使用权，不会释放对象锁，其他线程是不能获得对象锁
//                 对象锁lock在entryList中等待，其他线程是不能获取到她

//                    Thread.sleep(5000);
//                 ==》如果对象调用wait:
//                 该线程放弃CPU使用权，释放对象锁，该线程在waitset中等待，等待该对象唤醒该线程继续执行后续代码
//                 该对象锁可以被其他线程使用
                    lock.wait(5000);
                    log.debug("t1线程执行后续代码");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
        log.info("主线程.sleep(1)睡眠1秒，保证t1线程优先执行");

        //主线程睡眠，保证t1线程优先执行。
        sleep(2);
        synchronized (lock) {
            log.debug("主线程获得锁:lock");
        }

        synchronized (lockOthers) {
            log.debug("主线程获得锁:lockOthers");
        }
    }


    /**
     * 共享的room，达到线程安全的作用：
     */
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    /**
     * sleep: 测试条件满足：才能执行
     * 2020-07-13 17:09:58.658 [小南] DEBUG c.notify - 有烟没？[false]
     * 2020-07-13 17:09:58.662 [小南] DEBUG c.notify - 没烟，先歇会！
     * 2020-07-13 17:09:59.657 [送烟的] DEBUG c.notify - 烟到了噢！
     * 2020-07-13 17:10:00.662 [小南] DEBUG c.notify - 有烟没？[true] //小南线程必须睡足，2秒后醒过来，阻塞其他操作
     * 2020-07-13 17:10:00.662 [小南] DEBUG c.notify - 可以开始干活了
     * 2020-07-13 17:10:00.662 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-13 17:10:00.663 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-13 17:10:00.664 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-13 17:10:00.664 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-13 17:10:00.665 [其它人] DEBUG c.notify - 可以开始干活了
     */
    public static void test4() {
        //线程小南：
        new Thread(() -> {
            //获得room使用权：
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    //休息：2秒
                    Sleeper.sleep(2);
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        //其他人也想使用room做一些线程安全的操作
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                //小南线程在使用对象锁，小南线程没有释放对象锁，所有这里不能加锁成功
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }
        Sleeper.sleep(1);
        new Thread(() -> {
            // 这里能不能加 synchronized (room)？
            // 如果这里加 synchronized ，那么对象锁始终被小南线程占用，那么执行不了送烟的逻辑，那么等小南线程睡足2秒醒过来，hasCigarette始终false,什么也干不成
            hasCigarette = true;
            log.debug("烟到了噢！");
        }, "送烟的").start();
    }

    /**
     * wait:可以让其他线程并行运行，但会造成虚假唤醒。
     * lock.wiat()
     * 2020-07-14 14:26:31.879 [小南] DEBUG c.notify - 有烟没？[false]
     * 2020-07-14 14:26:31.883 [小南] DEBUG c.notify - 没烟，先歇会！
     * 2020-07-14 14:26:31.883 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-14 14:26:31.883 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-14 14:26:31.883 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-14 14:26:31.883 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-14 14:26:31.883 [其它人] DEBUG c.notify - 可以开始干活了
     * 2020-07-14 14:26:32.879 [送烟的] DEBUG c.notify - 烟到了噢！
     * 2020-07-14 14:26:32.880 [小南] DEBUG c.notify - 有烟没？[true]
     * 2020-07-14 14:26:32.880 [小南] DEBUG c.notify - 可以开始干活了
     */
    public static void test5() {
        //线程小南：
        new Thread(() -> {
            //获得room使用权：
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    //休息：2秒
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        //其他人也想使用room做一些线程安全的操作
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                //小南线程在使用对象锁，小南线程没有释放对象锁，所有这里不能加锁成功
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }
        Sleeper.sleep(1);
        new Thread(() -> {
            synchronized (room) {
                // 这里能不能加 synchronized (room)？
                // 如果这里加 synchronized ，那么对象锁始终被小南线程占用，那么执行不了送烟的逻辑，那么等小南线程睡足2秒醒过来，hasCigarette始终false,什么也干不成
                hasCigarette = true;
                log.debug("烟到了噢！");
                room.notify();
            }
        }, "送烟的").start();
    }

    /**
     * 测试虚假唤醒：
     */
    public static void test6() {
        //线程小南：
        new Thread(() -> {
            //获得room使用权：
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                //当hasCigarette=true,继续等待
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    //休息：2秒
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            //获得room使用权：
            synchronized (room) {
                log.debug("外卖到没有？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    //休息：2秒
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有外卖没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();
        Sleeper.sleep(1);
        new Thread(() -> {
            synchronized (room) {
                // 这里能不能加 synchronized (room)？
                // 如果这里加 synchronized ，那么对象锁始终被小南线程占用，那么执行不了送烟的逻辑，那么等小南线程睡足2秒醒过来，hasCigarette始终false,什么也干不成
                hasTakeout = true;
                log.debug("外卖到了噢！");
                //notify:随机唤醒一个waitset中的线程，这里会造成虚假唤醒
                room.notify();
                //notifyAll:全部唤醒
                room.notifyAll();
            }
        }, "送外卖的").start();
    }
}