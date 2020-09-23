package com.zlsoft.Locks8;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.util.Sleeper.sleep;

/**
 * 结果：
 *   -根据cpu的任务调度器：随机调度线程1，或者线程2，所有显示1、2或者显示2、1
 *   -当前synchronized锁定的是n1对象
 *   -当synchronized情况，无论上下文如何切换如何情况，在锁定的对象上，必须先执行完，才能支持第二个操作
 *   意思是当cpu任务调度器在执行了线程1的n1.a()的情况下，无论切换了多少次t2线程（t2获得时间片），n1.b()都不会执行，除非
 *   n1.b()执行完，释放了锁，
 */
@Slf4j(topic = "c.Test8Locks")
public class locks1 {
    public static void main(String[] args) {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin1");
            n1.a();
        },"t1").start();
        new Thread(() -> {
            log.debug("begin2");
            n1.b();
        },"t2").start();
        new Thread(() -> {
            log.debug("begin3");
            n1.c();
        },"t3").start();
    }
}

@Slf4j(topic = "c.Number")
class Number{
    public synchronized void a() {
        //5s后12，或 2 5s后 1
        sleep(1);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
    /**
     * 没有加synchronized，那么调用该方法他就不会互斥。
     */
    public void c() {
        log.debug("3");
    }
}
/**
 *  加上C方法：
 *   情况1: 3,2 1s 1
 *   情况2: 2,3 1s 1
 *   情况3: 3,1s 1,2
 * */
