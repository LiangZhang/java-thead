package com.zlsoft;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft
 * @ClassName: jolTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年07月01日 11:38:00
 */



class Person {

}
@Slf4j(topic = "c.TestBiased")
public class jolTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        test4();
    }
    /**
     * 用synchronized 加锁
     * 调用对象的hashcode可以撤销该对象的偏向锁
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        //自动开启偏向锁，禁用延迟
        //-XX:BiasedLockingStartupDelay=0
        //禁用偏向锁
        //-XX:-UseBiasedLocking
        //加锁前：声明一个对象：启用偏向锁
        Person d = new Person();
        log.debug("对象默认开启偏向锁：Biased_lock:1 + lock:2(101)" + ClassLayout.parseInstance(d).toPrintable());
        /**
         * ### 调用了对象的 hashCode，但偏向锁的对象 MarkWord 中存储的是线程 id，如果调用 hashCode 会导致偏向锁被撤销
         *   --轻量级锁会在锁记录中记录 hashCode
         *   --重量级锁会在 Monitor 中记录 hashCode
         */
        d.hashCode();
        log.debug("调用hashCode，撤销偏向锁,无锁状态：Biased_lock:1 + lock:2(001)" + ClassLayout.parseInstance(d).toPrintable());

        //加锁：
        synchronized (d) {
            //  00000010 01000001 10111000 00000101
            log.debug("加轻量级锁：lock:2 (00)" + ClassLayout.parseInstance(d).toPrintable());
        }

        //加锁后：
        log.debug("锁释放：无锁状态：Biased_lock:1 + lock:2(001)" + ClassLayout.parseInstance(d).toPrintable());
    }
}

