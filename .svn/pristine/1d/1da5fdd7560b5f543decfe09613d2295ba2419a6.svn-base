package com.zlsoft.threadPoolExecutor.cachedThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.fixedThreadPool
 * @ClassName: FixedThreadPoolTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月08日 17:15:00
 */
@Slf4j(topic = "zl.CachedThreadPoolTest")
public class CachedThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        //test1();
        //test2();
        test3();
    }

    /**
     * 评价 整个线程池表现为线程数会根据任务量不断增长，没有上限，当任务执行完毕，空闲 1分钟后释放线程。 适合任务数比较密集，但每个任务执行时间较短的情况
     */
    private static void test1() {
        ExecutorService pool = Executors.newCachedThreadPool();

        pool.execute(() -> {
            log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }


    //#region test2:输出结果
    /**
     java.util.concurrent.ThreadPoolExecutor@1888ff2c[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
     pool-1-thread-1
     pool-1-thread-2
     pool-1-thread-3
     pool-1-thread-4
     pool-1-thread-2
     pool-1-thread-2
     pool-1-thread-4
     pool-1-thread-1
     pool-1-thread-1
     java.util.concurrent.ThreadPoolExecutor@1888ff2c[Running, pool size = 12, active threads = 11, queued tasks = 0, completed tasks = 8]
     pool-1-thread-3
     pool-1-thread-5
     pool-1-thread-4
     pool-1-thread-9
     pool-1-thread-7
     pool-1-thread-2
     pool-1-thread-8
     pool-1-thread-6
     pool-1-thread-10
     pool-1-thread-11
     pool-1-thread-12
     */
    //#endregion
    /**
     * 复用性：
     *  -有些线程执行完任务后，会空闲下来，有新的任务提交时，会利用空闲线程执行。
     */
    private static void test2() {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);
        for (int i = 0; i < 20; i++) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        service.shutdown();
    }


    /**
     * 测试空闲线程60s销毁
     *  - keepAliveTime: 60L,
     *  -TimeUnit unit:TimeUnit.SECONDS
     */
    private static void test3() throws InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);
        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);
        TimeUnit.SECONDS.sleep(65);
        System.out.println(service);
    }


}
    
    
    