package com.zlsoft.basethreadmethod;

import com.zlsoft.core.Constants;
import com.zlsoft.core.SelfFileReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.basethreadmethod
 * @ClassName: ThreadMethodTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 线程的基础方法使用解析
 * @createTime 2020年10月12日 09:55:00
 */
@Slf4j(topic = "zl.ThreadMethodTest")
public class ThreadMethodTest {
    public static void main(String[] args){
        test1();
    }

    /**
     * thread.run()
     *  -如果直接调用，是主线程调用，而不是线程调用
     * @throws InterruptedException
     */
    private static void test1() {
        Thread.currentThread().setName("Main");

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("当前执行线程：{},读文件",Thread.currentThread().getName());
                SelfFileReader.read(Constants.MP4_FULL_PATH);
            }
        };
        //这里主线程打印，不是t1线程执行，不能进行异步或者并行编程效果
        t1.run();
        log.debug("当前执行线程：{},do other thing...");
    }
}