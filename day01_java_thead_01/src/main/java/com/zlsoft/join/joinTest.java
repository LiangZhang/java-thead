package com.zlsoft.join;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.join
 * @ClassName: joinTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年07月16日 09:53:00
 */
@Slf4j(topic = "c.joinTest")
public class joinTest {
    static int r = 0;
    static final Object lock = new Object();


    static int r1 = 0;
    static int r2 = 0;
    public static void main(String[] args) throws InterruptedException {
        test5();
    }

    /**
     * 等待一个结果：一个线程等待另一个线程结束
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        });
        t1.start();
        //等待线程t1.结束，当t1活着，继续wait,while (isAlive()) {wait(0);}
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }

    /**
     * 无实效的join() 等待多个结果：
     *  -因为join采用的是保护性暂停模式,wait，
     *  ### 2020-07-16 10:28:22.313 [main] INFO  c.joinTest - 1.主线程运行到当前代码时间差：42
     *     2020-07-16 10:28:24.312 [main] INFO  c.joinTest - 2.t2.join()后当前时间：2043
     *     2020-07-16 10:28:24.312 [main] INFO  c.joinTest - 3.t1.join()后当前时间：0
     *     2020-07-16 10:28:24.312 [main] INFO  c.joinTest - r1: 20
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(2);
            r1 = 20;
        });

        t1.start();
        t2.start();
        long end = System.currentTimeMillis();
        //如果：t1,t2:t1.join() 等待1秒，t2.join() 等待1秒
        //如果：t2,t1:t2.join()等待2秒，t1.join() 已经死掉 isAlive() = false,无需等待
        log.info("1.主线程运行到当前代码时间差：{}",end-start);
        t2.join();
        end = System.currentTimeMillis();
        log.info("2.t2.join()后当前时间：{}",end-start);
        start = System.currentTimeMillis();
        t1.join();
        end = System.currentTimeMillis();
        log.info("3.t1.join()后当前时间：{}",end-start);
        log.info("r1: {}", r1);
    }

    /**
     * 无实效的join() 等待多个结果
     *  ### 2020-07-16 10:30:43.587 [main] INFO  c.joinTest - 1.主线程运行到当前代码时间差：45
     * 	    2020-07-16 10:30:44.586 [main] INFO  c.joinTest - 2.t1.join()后当前时间：1046
     * 	    2020-07-16 10:30:45.586 [main] INFO  c.joinTest - 3.t2.join()后当前时间：1000
     * 	    2020-07-16 10:30:45.586 [main] INFO  c.joinTest - r1: 20
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(2);
            r1 = 20;
        });

        t1.start();
        t2.start();
        long end = System.currentTimeMillis();
        //如果：t1,t2:t1.join() 等待1秒，t2.join() 等待1秒
        //如果：t2,t1:t2.join()等待2秒，t1.join() 已经死掉 isAlive() = false,无需等待
        log.info("1.主线程运行到当前代码时间差：{}",end-start);
        t1.join();
        end = System.currentTimeMillis();
        log.info("2.t1.join()后当前时间：{}",end-start);
        start = System.currentTimeMillis();
        t2.join();
        end = System.currentTimeMillis();
        log.info("3.t2.join()后当前时间：{}",end-start);
        log.info("r1: {}", r1);
    }


    /**
     * 有实效的join:
     *  -如果join时间大于线程alive(),等待根据线程结束时间而结束
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        long start = System.currentTimeMillis();
        log.info("1.主线程开始时间：{}",start);
        t1.start();
        // 线程执行结束会导致 join 结束
        t1.join(1500);
        if(!t1.isAlive()) {
            log.info("1.主线程开始时间：{}",start);
        }
        long end = System.currentTimeMillis();
        log.info("2.t1.join(1500)后当前时间：{},执行时长：{}",end,end-start);
    }

    /**
     * 有实效的join:
     *  -如果join时间小于线程alive(),等待根据等待时间而结束
     * @throws InterruptedException
     */
    private static void test5() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(3);
            r1 = 10;
        });
        long start = System.currentTimeMillis();
        log.info("1.主线程开始时间：{}",start);
        t1.start();
        // 线程执行结束会导致 join 结束
        t1.join(1500);
        if(!t1.isAlive()) {
            log.info("1.主线程开始时间：{}",start);
        }
        long end = System.currentTimeMillis();
        log.info("2.t1.join(1500)后当前时间：{},执行时长：{}",end,end-start);
    }



//    private static void test5() throws InterruptedException {
//        Thread t1 = new Thread(() -> {
//            sleep(1);
//            r1 = 10;
//        });
//        long start = System.currentTimeMillis();
//        log.info("1.主线程开始时间：{}",start);
//        t1.start();
//        // 线程执行结束会导致 join 结束
//        selfJoin(t1,1500);
//        long end = System.currentTimeMillis();
//        log.info("2.t1.join(1500)后当前时间：{},执行时长：{}",end,end-start);
//    }
//
//    private static final synchronized void selfJoin(Thread t1,long millis) throws InterruptedException {
//        long base = System.currentTimeMillis();
//        long now = 0;
//
//        if (millis < 0) {
//            throw new IllegalArgumentException("timeout value is negative");
//        }
//
//        if (millis == 0) {
//            while (t1.isAlive()) {
//                t1.wait(0);
//            }
//        } else {
//            while (t1.isAlive()) {
//                long delay = millis - now;
//                log.info("判断线程是否还需要继续等待：");
//                if (delay <= 0) {
//                    break;
//                }
//                t1.wait(delay);
//                now = System.currentTimeMillis() - base;
//            }
//        }
//    }



}