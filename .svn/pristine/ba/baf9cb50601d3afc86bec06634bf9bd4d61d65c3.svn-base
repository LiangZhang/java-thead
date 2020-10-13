package com.zlsoft.threadPoolExecutor.delay;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.delay
 * @ClassName: ScheduledTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月14日 16:53:00
 */
@Slf4j(topic = "zl.ScheduledTest")
public class ScheduledTest {
    public static void main(String[] args) {
        test1();
    }

    /**
     * 某个时间定时执行:如果在每周四：18:00:00 定时执行任务
     * long initialDelay:当前时间和定时时间(周四)的时间差
     * long period:间隔多少（每周的时间间隔）
     */
    private static void test1() {

        //一周的毫秒数：
        long period = 1000 * 60 * 60 *  24 * 7;
        log.debug("一周毫秒数：{}",period);


        //当前时间：
        LocalDateTime now = LocalDateTime.now();
        log.debug("当前时间：{}",now);

        //周四时间：
        LocalDateTime time = now.withHour(18).withMinute(26).withSecond(0).withNano(0).with(DayOfWeek.MONDAY);
        log.debug("周四时间：{}",time);

        //过期增加一周
        if(now.compareTo(time) > 0) {
            time = time.plusWeeks(1);
        }

        long initialDelay = Duration.between(now,time).toMillis();

        log.debug("当前时间到周四时间的差值：{}", Duration.between(now,time).toMillis());


        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(()->{
            log.debug("定时执行时间到了：{}","sdfdsf");
        },initialDelay, period, TimeUnit.MICROSECONDS);
    }
}
    
    
    