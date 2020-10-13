package com.zlsoft.threadPoolExecutor.singleThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.singleThreadPool
 * @ClassName: SingleThreadPoolTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 运用场景：
 * 在一个api中，程序的最后要调用一个存储过程，但是存储过程调用完需要花费很多的时间，程序并并不关心最后调的存储过程的执行结果。
 * 所以想到最后重新开一个线程来调用储存过程 ，主线程直接返回结果。但是由于储存过程执行的时间很长，所以用户同时访问的时候很容易出现
 * 几个调用存储过程的线程在后台同时运行，如果他们操作了同一张表的同一条数据， 就容易导致死锁。
 * 所以我们希望调用存储过程的线程一次只有一个线程执行。这个时候就可以用只有一个线程的线程池。
 * @createTime 2020年09月09日 15:04:00
 */
@Slf4j(topic = "zl.SingleThreadPoolTest")
public class SingleThreadPoolTest {

    private static ExecutorService simpleExecutor = Executors.newSingleThreadExecutor();
    public static void main(String[] args) {
        test1();
    }
    private static void test1() {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingleThreadPoolTest threadQueueTest = new SingleThreadPoolTest();
                threadQueueTest.call();
            }).start();

//            threads[i] = new Thread() {
//                @Override
//                public void run() {
//                    SingleThreadPoolTest threadQueueTest = new SingleThreadPoolTest();
//                    threadQueueTest.call();
//                }
//            };
//            threads[i].start();
        }
    }
    public void call() {
        //各种业务逻辑代码
        //..
        //..
        //..
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("各种正常的业务逻辑执行完了");
        //开启另外一个线程开始工作
        simpleExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info(Thread.currentThread().getName());
                log.info("单线程程序运行完了");
                return null;
            }
        });
        log.info("主程序执行完了");
    }
}
    
    
    