package com.zlsoft;

import lombok.extern.slf4j.Slf4j;

import static com.zlsoft.util.Sleeper.sleep;

/**
 * 结果：
 *   -根据cpu的任务调度器：随机调度线程1，或者线程2，所有显示1、2
 * 当前synchronized锁定的是n1对象
 */
@Slf4j(topic = "c.Test8Locks")
public class Test8Locks {
    public static void main(String[] args) {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }).start();
        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }).start();
    }
}

@Slf4j(topic = "c.Number")
class Number{
    public synchronized void a() {
        sleep(1);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
}
