package com.zlsoft.park;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.park
 * @ClassName: ParkTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年07月16日 10:57:00
 */
@Slf4j(topic = "c.parkTest")
public class ParkTest {
    public static void main(String[] args) {
       test2();
    }

    /**
     * 阻止、恢复线程运行
     */
    private static void test1() {
        Thread t1 = new Thread(() -> {
            log.debug("t1线程：start...");
            sleep(1);
            log.debug("t1线程：park...");
            //暂停当前线程
            LockSupport.park();
            log.debug("t1线程恢复执行：resume...");
        },"t1");
        t1.start();
        sleep(2);
        log.debug("当前线程t1状态：wait,主线程执行代码：unpark...");
        //恢复指定线程
        LockSupport.unpark(t1);
        log.debug("当前线程t1状态：running");
    }


    /**
     * unpark可以先执行，park先执行:
     *   -park对象由底层c实现，java层面是不能看到的
     * 2020-07-16 11:16:54.444 [t1] DEBUG c.parkTest - t1线程：start...
     * 2020-07-16 11:16:55.444 [main] DEBUG c.parkTest - 当前线程t1状态：wait,主线程执行代码：unpark...
     * 2020-07-16 11:16:55.445 [main] DEBUG c.parkTest - 当前线程t1状态：running
     * 2020-07-16 11:16:58.447 [t1] DEBUG c.parkTest - t1线程：park...
     * 2020-07-16 11:16:58.447 [t1] DEBUG c.parkTest - t1线程恢复执行：resume...
     */
    private static void test2() {
        Thread t1 = new Thread(() -> {
            log.debug("t1线程：start...");
            sleep(4);
            log.debug("t1线程：park...");
            //暂停当前线程
            LockSupport.park();
            log.debug("t1线程恢复执行：resume...");
        },"t1");
        t1.start();
        sleep(1);
        log.debug("当前线程t1状态：wait,主线程执行代码：unpark...");
        //恢复指定线程
        LockSupport.unpark(t1);
        log.debug("当前线程t1状态：running");
    }
}