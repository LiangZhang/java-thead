package com.zlsoft.threadState;

import com.zlsoft.core.Sleeper;
import com.zlsoft.core.TranslationClassLayout;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadState
 * @ClassName: ThreadState.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年07月17日 17:50:00
 */
@Slf4j(topic = "c.ThreadState")
public class ThreadState {
    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 =new Thread(() -> {
//            log.info("t1线程启动，线程状态：{}","run");
            synchronized (obj) {
                log.debug("t1线程执行....");
                log.debug("t1程执行，obj markword: {}",new TranslationClassLayout(obj).toMarkWord());
                try {
                    obj.wait(50000); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }log.debug("t1程执行，obj markword: {}",new TranslationClassLayout(obj).toMarkWord());
                log.debug("t1其它代码....");
            }
        },"t1");
        t1.start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("t2执行....");
                log.debug("t2程执行，obj markword: {}",new TranslationClassLayout(obj).toMarkWord());
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2程执行，obj markword: {}",new TranslationClassLayout(obj).toMarkWord());
                log.debug("t2其它代码....");
            }
        },"t2").start();

        // 主线程两秒后执行
        Sleeper.sleep(2.5);
        log.debug("主线程执行，obj markword: {}",new TranslationClassLayout(obj).toMarkWord());
        log.debug("唤醒 obj 上其它线程");
        synchronized (obj) {
//            obj.notify(); // 唤醒obj上一个线程
            log.debug("主线程执行，obj markword: {}",new TranslationClassLayout(obj).toMarkWord());
            obj.notifyAll(); // 唤醒obj上所有等待线程
        }
    }

    /**
     * t1 线程用 synchronized(obj) 获取了对象锁后
     *    A.调用 obj.wait() 方法时，t 线程从 RUNNABLE --> WAITING
     *    b.调用 obj.notify() ， obj.notifyAll() ， t.interrupt() 时
     *         竞争锁成功，t 线程从 WAITING --> RUNNABLE
     *         竞争锁失败，t 线程从 WAITING --> BLOCKED
     */
}