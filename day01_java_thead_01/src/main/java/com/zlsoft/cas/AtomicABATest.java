package com.zlsoft.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: AtomicABATest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年08月26日 17:24:00
 */
@Slf4j(topic = "z.atomicABATest")
public class AtomicABATest {

    /**
     * 保护多个线程对一个对象的引用的安全使用
     */
    static AtomicReference<String> ref = new AtomicReference<>("A");
    /**
     * 带版本的更新
     */
    static AtomicStampedReference<String> referenceABA = new AtomicStampedReference<>("A",0);

    public static void main(String[] args) {
        /**
         * ABA问题：
         */
//        test1();
        test2();
    }

    //#region ABA问题
    private static void test1() {
        log.error("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.get();
        other1();
        sleep(1);
        // 尝试改为 C
        log.error("change A->C {}", ref.compareAndSet(prev, "C"));
    }

    private static void other1() {
        new Thread(() -> {
            log.error("change A->B {}", ref.compareAndSet(ref.get(), "B"));
        }, "t1").start();
        sleep(0.5);

        new Thread(() -> {
            log.error("change B->A {}", ref.compareAndSet(ref.get(), "A"));
        }, "t2")
                .start();
    }
    //#endregion

    //#region 解决ABA问题
    private static void test2() {
        log.error("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = referenceABA.getReference();
        int stampe = referenceABA.getStamp();
        log.error("当前版本号：{}",stampe);
        other2();
        sleep(2);
        log.error("尝试改为 C,当前版本号：{}",stampe);
        log.error("change A->C {}", referenceABA.compareAndSet(prev, "C",stampe,stampe + 1));

        stampe = referenceABA.getStamp();
        log.error("尝试改为 C,获取当前最新版本号：{}",stampe);
        log.error("change A->C {}", referenceABA.compareAndSet(prev, "C",stampe,stampe + 1));
    }

    private static void other2() {
        new Thread(() -> {
            int stamped = referenceABA.getStamp();
            log.error("change A->B,当前版本号：{}",stamped);
            log.error("change A->B {}", referenceABA.compareAndSet(referenceABA.getReference(), "B",stamped,stamped + 1));
        }, "t1").start();
        sleep(0.5);

        new Thread(() -> {
            int stamped = referenceABA.getStamp();
            log.error("change B-> A,当前版本号：{}",stamped);
            log.error("change B-> A {}", referenceABA.compareAndSet(referenceABA.getReference(), "A",stamped,stamped + 1));
        }, "t2")
                .start();
    }

    //#endregion

}
    
    
    