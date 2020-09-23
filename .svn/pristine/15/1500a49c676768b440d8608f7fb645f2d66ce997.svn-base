package com.zlsoft.patten.guarded;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.patten
 * @ClassName: TestGuardedObject.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description Guarded Suspension（保护性暂停模式）：
 *      -多个线程通过一个guarded对象进行交互数据
 * ###  -有一个结果需要从一个线程传递到另一个线程，让他们关联同一个 GuardedObject
 *      -如果有结果不断从一个线程到另一个线程那么可以使用消息队列（见生产者/消费者）
 *      -JDK 中，join 的实现、Future 的实现，采用的就是此模式
 *      -因为要等待另一方的结果，因此归类到同步模式
 */
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static com.zlsoft.patten.Downloader.download;

@Slf4j(topic = "c.SingleGuardedObject")
public class SingleGuardedObject {
    /**
     * 线程1等待线程2下载结果
     * @param args
     */
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();

        //t1:线程获取结果
        new Thread(() -> {
            log.debug("t1线程等待下载结果...");
            List<String> list = (List<String>) guardedObject.get();
            log.debug("t1线程获得下载结果大小：{}", list.size());
        }, "t1").start();

        //t2：线程生产结果
        new Thread(() -> {
            try {
                log.debug("t2线程执行下载...");
                List<String> response = download();
                log.debug("t2下载完成，传输结果到t1线程...");
                //传输结果到t1线程
                guardedObject.complete(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

/**
 * 保护对象
 */
class GuardedObject {
    //桥梁：保存线程之间的结果
    private Object response;
    private final Object lock = new Object();

    /**
     * 获取结果线程：等待其他的线程产生结果
     * @return
     */
    public Object get() {
        synchronized (lock) {
            // 条件不满足则等待
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    /**
     * 产生结果，为response赋值：
     * @param response
     */
    public void complete(Object response) {
        synchronized (lock) {
            // 条件满足，通知等待线程
            this.response = response;
            lock.notifyAll();
        }
    }
}