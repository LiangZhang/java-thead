package com.zlsoft.summarize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.Uppark")
public class Uppark {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            //单独有干粮执行后续代码
            LockSupport.park();
            log.debug("1");
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("2");
            //先准备干粮
            LockSupport.unpark(t1);
        },"t2").start();
    }
}