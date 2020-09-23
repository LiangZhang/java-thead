package com.zlsoft.threadPoolExecutor.threadPoolSubmit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.threadPoolSubmit
 * @ClassName: ThreadPoolSubmitTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月09日 16:39:00
 */
@Slf4j(topic = "zl.ThreadPoolSubmitTest")
public class ThreadPoolSubmitTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }


    private static void test1() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("running");
                Thread.sleep(1);
                return "ok";
            }
        });
        log.debug("主线程返回：");
        log.debug("取得有返回结果的线程池：{}", future.get());
        log.debug("主线程返回结束：");
    }

    private static void test2() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(() -> {
            log.debug("running");
            Thread.sleep(1000);
            return "ok";
        });
        log.debug("主线程返回：");
        log.debug("取得有返回结果的线程池：{}", future.get());
        log.debug("主线程返回结束：");
    }

}
    
    
    