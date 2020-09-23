package com.zlsoft.threadPoolExecutor.delay;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.delay
 * @ClassName: TimerTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 任务延迟执行
 * @createTime 2020年09月10日 17:31:00
 */
@Slf4j(topic = "zl.TimerTest")
public class TimerTest {

    public static void main(String[] args) {
        test6();
    }

    /**
     * 测试延时执行：
     * 延时、定时执行任务：
     *  - 同一个线程执行，任务是串行执行
     *  - 如果有一个任务失败，其他任务则自动终止
     *  return:
     *  17:40:07.827 zl.TimerTest [main] - main start...
     *  17:40:08.852 zl.TimerTest [Timer-0] - task 1
     *  17:40:08.852 zl.TimerTest [Timer-0] - task 2
     */
    private static void test1() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };

        log.debug("main start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

    /**
     * 测试串行执行：
     */
    private static void test2() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("延时2秒后任务才结束：task 1");
                sleep(2);
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };
        log.debug("main start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

    /**
     * 测试异常终止任务：
     */
    private static void test3() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("该任务出现异常：task 1");
                int i = 5/0;
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };
        log.debug("main start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }


    /**
     * 用有任务调度的线程池newScheduleThreadPool来代码timer
     */
    private static void test4() {
        //有任务调度的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
       pool.schedule(()->{
           log.debug("task 1");
           sleep(2);
       },1,TimeUnit.SECONDS);
        pool.schedule(()->{
            log.debug("task 1");
            sleep(2);
        },1,TimeUnit.SECONDS);
    }

    /**
     * 定时以一定时间间隔执行
     *  - 如果执行任务时间超过定义间隔时间，则以任务时间为准
     */
    private static void test5() {
        //有任务调度的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("定时执行...");
        pool.scheduleAtFixedRate(()->{
            log.debug("task ...");
            sleep(2);
                },
                1,//延时，
                2,// 间隔
               TimeUnit.SECONDS );
    }

    /**
     * 定时以一定时间间隔执行
     *  - 定时以任务执行完成后，在延迟间隔时间，在定时执行任务
     */
    private static void test6() {
        //有任务调度的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("定时执行...");
        pool.scheduleWithFixedDelay(()->{
                    log.debug("task ...");
                    sleep(3);
                },
                1,//延时，
                2,// 间隔
                TimeUnit.SECONDS );
    }



    private static void method3() {
        //有任务调度的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);
    }

    private static void method2(ScheduledExecutorService pool) {
        pool.schedule(() -> {
            log.debug("task1");
            int i = 1 / 0;
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }



}
    
    
    