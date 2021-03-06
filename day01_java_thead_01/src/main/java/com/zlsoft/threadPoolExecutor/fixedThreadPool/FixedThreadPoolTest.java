package com.zlsoft.threadPoolExecutor.fixedThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
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
@Slf4j(topic = "zl.FixedThreadPoolTest")
public class FixedThreadPoolTest {
    public static void main(String[] args) {
        test1();
        //test2();
    }

    /**
     * 固定大小线程池使用：
     *  -核心线程执行任务时候不会主动结束任务，核心线程继续运行...
     *  -核心线程数 == 最大线程数（没有救急线程被创建），因此也无需超时时间
     *  -阻塞队列是无界的，可以放任意数量的任务
     */
    private static void test1() {
        ExecutorService pool = Executors.newFixedThreadPool(2);

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

    private static void test2() {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger threadId = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "my-pool-" + threadId.getAndIncrement());
            }
        });

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
}
    
    
    