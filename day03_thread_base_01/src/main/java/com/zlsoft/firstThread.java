package com.zlsoft;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft
 * @ClassName: firstThread.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年06月15日 13:54:00
 */
@Slf4j(topic = "c.firstThread")
public class firstThread {
    public static void main(String[] args) {
        /**
         * 创建一个线程对象：
         */
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread0 running");
            }
        };
        thread0.start();

        Thread thread1 = new Thread(() -> {
            log.info("thread1 running");
        });
        thread1.start();


        /**
         * 创建任务对象
         */
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("thread2 runnable running");
            }
        };
        Thread thread2 = new Thread(runnable, "t2");
        thread2.start();


        /**
         * 如果一个借口只有一个方法，
         * 有注解：@FunctionalInterface，就可以lambda表达式简化。
         */
        Runnable runnable1 = () -> {
            log.info("thread3 lambda runnable");
        };
        Thread thread3 = new Thread(runnable1, "t4");
        thread3.start();


        log.info("main running");
    }
}