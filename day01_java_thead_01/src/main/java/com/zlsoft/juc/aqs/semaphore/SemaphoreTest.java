package com.zlsoft.juc.aqs.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.juc.aqs.semaphore
 * @ClassName: SemaphoreTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020年10月09日 10:45:00
 * @Description 信号量
 * 作用：
 *  - 限制同一时刻线程访问共享资源的上限，reentrantlock本质是独占的，同一时刻只有一个线程
 */
@Slf4j(topic = "c.TestSemaphore")
public class SemaphoreTest {
    public static void main(String[] args) {
        test1();
    }

    /**
     * 限制可以访问共享资源：个数
     */
    private static void test1() {
        // 1. 创建 semaphore 对象
        Semaphore semaphore = new Semaphore(3);

        // 2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    sleep(1);
                    log.debug("end...");
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
    
    
    