package com.zlsoft.deadlock.v1;

import com.zlsoft.core.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家就餐问题
 * 有五位哲学家，围坐在圆桌旁。
 *   -他们只做两件事，思考和吃饭，思考一会吃口饭，吃完饭后接着思考。
 *   -吃饭时要用两根筷子吃，桌上共有 5 根筷子，每位哲学家左右手边各有一根筷子。
 *   -如果筷子被身边的人拿着，自己就得等待
 */

public class TestDeadLock {

    private static Map<String,Integer> countNum = new HashMap();

    public static void main(String[] args) {
        countNum.put("苏格拉底",0);
        countNum.put("柏拉图",0);
        countNum.put("亚里士多德",0);
        countNum.put("赫拉克利特",0);
        countNum.put("阿基米德",0);

        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        new Philosopher("苏格拉底", c1, c2,countNum).start();
        new Philosopher("柏拉图", c2, c3,countNum).start();
        new Philosopher("亚里士多德", c3, c4,countNum).start();
        new Philosopher("赫拉克利特", c4, c5,countNum).start();
        //顺序执行解决死锁的问题:但是产生饥饿问题
        new Philosopher("阿基米德", c1, c5,countNum).start();
    }
}

@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread {
    Chopstick left;
    Chopstick right;
    Map<String,Integer> countNum;

    public Philosopher(String name, Chopstick left, Chopstick right,Map countNum) {
        super(name);
        this.left = left;
        this.right = right;
        this.countNum = countNum;
    }

    @Override
    public void run() {
        while (true) {
            //　尝试获得左手筷子
            synchronized (left) {
                // 尝试获得右手筷子
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    Random random = new Random();

    /**
     * 吃饭
     */
    private void eat() {

        //使用顺序加锁的方式解决之前的死锁问题,但是有的线程会造成饥饿现象，比如这里的阿基米德
//        if(this.currentThread().getName().equals("阿基米德")){
//            log.debug("{} eating...",this.currentThread().getName());
//        }


        String threadName = this.currentThread().getName();
        int count = countNum.get(threadName);
        count++;
        countNum.put(threadName,count);
        log.debug("苏格拉底总共吃了{}次，柏拉图总共吃了{}次，亚里士多德总共吃了{}次，赫拉克利特总共吃了{}次，阿基米德总共吃了{}次",
                countNum.get("苏格拉底"),
                countNum.get("柏拉图"),
                countNum.get("亚里士多德"),
                countNum.get("赫拉克利特"),
                countNum.get("阿基米德")
        );
        Sleeper.sleep(0.5);
    }
}

class Chopstick {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}