package com.zlsoft.summarize;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.summarize
 * @ClassName: AwaitSignal.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年08月03日 13:41:00
 */
public class AwaitSignal {
    public static void main(String[] args) {
        AwaitSignal2 as = new AwaitSignal2(1,5);
        new Thread(()->{
            as.print("a",1,2);
        }).start();

        new Thread(()->{
            as.print("b",2,3);
        }).start();

        new Thread(()->{
            as.print("c",3,1);
        }).start();


    }
}

@Slf4j(topic = "c.Awaitsignal")
class AwaitSignal2 extends ReentrantLock {
    //等待标记
    private int flag;

    // 循环次数
    private int loopNumber;

    public AwaitSignal2(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /**
     * 多个线程打印：交替打印方法：abcabcabcabcabcabc
     *
     * 可见性
     *
     */

    public void print(String str,int waitFlag,int nextFlag){
        for (int i = 0; i < this.loopNumber; i++) {
            synchronized (this) {
                while (waitFlag != flag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                //等待标记变成下一个标记
                flag = nextFlag;
                //唤醒所有在waitset等待的线程
                this.notifyAll();
            }
        }
    }

}
    
    
    