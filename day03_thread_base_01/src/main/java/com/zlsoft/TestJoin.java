package com.zlsoft;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.util.Sleeper.sleep;

/**
 * join-限时同步线程:
 *    -t1.join()
 *    -有实效的等待 t1.join(3000),有实效的等待，如果线程提前结束，则等待结束，
 *    -如果线程没有在等待时间结束，按实效等待时间结束线程。
 */
@Slf4j(topic = "c.TestJoin")
public class TestJoin {
    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(2);
            r1 = 10;
        });

        long start = System.currentTimeMillis();
        t1.start();

        // 线程执行结束会导致 join 结束
        log.debug("join begin");
        t1.join(1500);
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    /**
     * t1,t2线程是异步并行执行的。
     * 当t1.join的时候，t2也在执行
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(2);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(10);
            r2 = 20;
        });
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join begin");
        t2.join(3000);
        log.debug("t2 join end");
        t1.join();
        log.debug("t1 join end");
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        });
        t1.start();
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
