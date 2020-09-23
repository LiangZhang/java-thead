package com.zlsoft;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test11")
public class Test11 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                //对于sleep、wait、join等操作的interrupt，打断标志是false
                Thread.sleep(5000); // wait, join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        t1.start();
        //主线程sleep 1秒
        Thread.sleep(1000);
        log.debug("interrupt");
        //打断t1线程睡眠
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
    }
}
