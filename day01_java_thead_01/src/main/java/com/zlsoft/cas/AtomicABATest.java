package com.zlsoft.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.zlsoft.core.Sleeper.sleep;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: AtomicABATest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 原子引用：ABA问题
 *    -AtomicStampedReference
 *    -AtomicMarkableReference
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

    static AtomicMarkableReference<String> referenceMarkable = new AtomicMarkableReference<String>("A",true);

    public static void main(String[] args) {
        /**
         * ABA问题：
         */
//        test1();
//        test2();

        test3();
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

    //#region 仅仅关注最终结果
    private static void test3() {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        // 参数2 mark 可以看作一个标记，表示垃圾袋满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);

        log.error("start...");
        GarbageBag prev = ref.getReference();
        log.error(prev.toString());

        new Thread(() -> {
            log.error("start...");
            bag.setDesc("空垃圾袋");
            ref.compareAndSet(bag, bag, true, false);
            log.error(bag.toString());
        },"保洁阿姨").start();

        sleep(1);
        log.error("想换一只新垃圾袋？");
        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.error("换了么？" + success);
        log.error(ref.getReference().toString());
    }
    //#endregion

    //#region 原子数组
    /**
     参数1，提供数组、可以是线程不安全数组或线程安全数组
     参数2，获取数组长度的方法
     参数3，自增方法，回传 array, index
     参数4，打印数组的方法
     */
    private static <T> void test4(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer ) {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            // 每个线程对数组作 10000 次操作
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array, j%length);
                }
            }));
        }

        ts.forEach(t -> t.start()); // 启动所有线程
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });     // 等所有线程结束
        printConsumer.accept(array);
    }

    //#endregion

}

/**
 * 垃圾袋：
 */
class GarbageBag {
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}
    
    
    