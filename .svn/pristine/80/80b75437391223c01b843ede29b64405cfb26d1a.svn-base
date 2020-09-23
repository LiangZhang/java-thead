package com.zlsoft.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: LongAdderTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月02日 12:00:00
 */
public class LongAdderTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test1();
        }
        System.out.println("start");
        for (int i = 0; i < 10; i++) {
            test2();
        }

    }

    /**
     * Atomic累加器
     */
    private static void test1() {
        demo(
                ()-> new AtomicLong(0),
                (adder)-> adder.getAndIncrement());
    }

    private static void test2() {
        demo(
                ()-> new LongAdder(),
                (adder)-> adder.increment());
    }


    /*
    () -> 结果    提供累加器对象
    (参数) ->     执行累加操作
     */
    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        // 4 个线程，每人累加 50 万
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end - start) / 1000_000);
    }
}
    
    
    