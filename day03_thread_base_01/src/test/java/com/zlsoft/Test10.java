package com.zlsoft;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.util.Sleeper.sleep;

@Slf4j(topic = "c.Test10")
public class Test10 {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
     * 主线程main和t1线程是并行执行的
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(5);
            log.debug("结束");
            r = 10;
        },"t1");
        t1.start();
        // 等待t1线程结束，在运行其他线程
        // 线程的同步
        t1.join();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
