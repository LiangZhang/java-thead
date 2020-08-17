package com.zlsoft.patten.guarded;

import com.zlsoft.core.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * 多任务版 GuardedObject
 */
@Slf4j(topic = "c.MTaskGuardedObject")
public class MTaskGuardedObject {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Sleeper.sleep(3);
        for (Integer id : Mailboxes.getIds()) {
            new Postman(id, "内容" + id).start();
        }
    }
}

/**
 * 居民类:消费者
 */
@Slf4j(topic = "c.People")
class People extends Thread{
    @Override
    public void run() {
        // 收信
        MGuardedObject guardedObject = Mailboxes.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id:{}, 内容:{}", guardedObject.getId(), mail);
    }
}

@Slf4j(topic = "c.Postman")
/**
 * 邮递员类：生成者：
 */
class Postman extends Thread {
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        MGuardedObject guardedObject = Mailboxes.getGuardedObject(id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        guardedObject.complete(mail);
    }
}

/**
 * 解耦类：
 *   邮箱附属类
 * rpc框架很多地方采用这种模式
 *   - 远程过程调用协议RPC（Remote Procedure Call Protocol)
 */
class Mailboxes {
    //该集合需要多个线程访问：Hashtable线程安全
    private static Map<Integer, MGuardedObject> boxes = new Hashtable<>();

    private static int id = 1;
    // 产生唯一 id：用synchronized保证原子性
    private static synchronized int generateId() {
        return id++;
    }

    public static MGuardedObject getGuardedObject(int id) {
        return boxes.remove(id);
    }

    /**
     * 在这里创建guardedObject对象，解耦
     * @return
     */
    public static MGuardedObject createGuardedObject() {
        MGuardedObject go = new MGuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    /**
     * 因为boxse的类型是Hashtable，是线程安全的，所有，这里不用加synchronized
     * @return
     */
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}

// 增加超时效果
class MGuardedObject {

    // 标识 Guarded Object
    private int id;

    public MGuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // 结果
    private Object response;

    // 获取结果
    // timeout 表示要等待多久 2000
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间 15:00:00
            long begin = System.currentTimeMillis();
            // 经历的时间
            long passedTime = 0;
            while (response == null) {
                // 这一轮循环应该等待的时间
                long waitTime = timeout - passedTime;
                // 经历的时间超过了最大等待时间时，退出循环
                if (timeout - passedTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime); // 虚假唤醒 15:00:01
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 求得经历时间
                passedTime = System.currentTimeMillis() - begin; // 15:00:02  1s
            }
            return response;
        }
    }

    // 产生结果
    public void complete(Object response) {
        synchronized (this) {
            // 给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}

