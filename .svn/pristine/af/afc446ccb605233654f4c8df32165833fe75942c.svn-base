package com.zlsoft;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test12")
public class Test12 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted) {
                    log.debug("被打断了, 退出循环");
                    break;
                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                log.info("t1 running...");
            }
        }, "t1");
        t1.start();
        log.info("main running...'");
        Thread.sleep(5000);
        log.debug("interrupt:优雅的停止线程...");
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
    }
}
