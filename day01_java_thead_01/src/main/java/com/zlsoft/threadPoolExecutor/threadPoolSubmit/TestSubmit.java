package com.zlsoft.threadPoolExecutor.threadPoolSubmit;

import com.zlsoft.core.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.threadPoolSubmit
 * @ClassName: TestSubmit.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月14日 15:41:00
 */
@Slf4j(topic = "zl.TestSubmit")
public class TestSubmit {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        test3();
    }

    /**
     * shutdown
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<String> result1 = pool.submit(() -> {
            log.debug("task 1 running...");
            Sleeper.sleep(1);
            log.debug("task 1 finish...");
            return "result1";
        });

        Future<String> result2 = pool.submit(() -> {
            log.debug("task 2 running...");
            Sleeper.sleep(1);
            log.debug("task 2 finish...");
            return "result2";
        });

        /**
         * 放入阻塞队列，等待空闲线程执行：
         */
        Future<String> result3 = pool.submit(() -> {
            log.debug("task 3 running...");
            Sleeper.sleep(1);
            log.debug("task 3 finish...");
            return "result3";
        });
        /**
         * -1.不会阻塞主线程的执行
         * -2.当结束线程池，在往线程池提交任务，则 （java.util.concurrent.RejectedExecutionException）抛出拒绝策略异常
         */
        pool.shutdown();

        /**
         * 线程池等待多时时间，在执行主线程的任务
         */
        pool.awaitTermination(1, TimeUnit.SECONDS);
        log.debug("main thread do others...");

//        Future<String> result4= pool.submit(() -> {
//            log.debug("task 4 running...");
//            Sleeper.sleep(1);
//            log.debug("task 4 finish...");
//            return "result4";
//        });


    }

    /**
     * shutdownNow
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<String> result1 = pool.submit(() -> {
            log.debug("task 1 running...");
            Sleeper.sleep(5);
            log.debug("task 1 finish...");
            return "result1";
        });

        Future<String> result2 = pool.submit(() -> {
            log.debug("task 2 running...");
            Sleeper.sleep(5);
            log.debug("task 2 finish...");
            return "result2";
        });

        /**
         * 放入阻塞队列，等待空闲线程执行：
         */
        Future<String> result3 = pool.submit(() -> {
            log.debug("task 3 running...");
            Sleeper.sleep(5);
            log.debug("task 3 finish...");
            return "result3";
        });
        /**
         * 返回当前未执行完的任务
         */
        List<Runnable> runnables = pool.shutdownNow();
        log.debug("thread undo...{}.",runnables);
        //runnables.get(0).run();
    }

    /**
     * 处理异常
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<Integer> result1 = pool.submit(() -> {
            log.debug("task 1 running...");
            Sleeper.sleep(5);
            //int i = 1 / 0;
            log.debug("task 1 finish...");
            return 1;
        });
        log.debug("result: {}",result1.get());
    }




}
    
    
    