package com.zlsoft.cas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.*;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: AtomicArrayTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 原子数组：
 *   - 解决如果要保护原子引用对象里面的属性，则不行
 *   - 针对数组或者对象里面保护
 *   - 测试多线程访问数组安全问题。
 * @createTime 2020年09月02日 08:43:00
 */
public class AtomicArrayTest {
    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
     * 非原子数组操作
     */
    private static void test1() {
        functionInterfaceDemo(
//                () -> new Integer[10],
                ()->new int[10],
                (array) -> array.length,
                (array,index)-> array[index]++,
                (array) -> System.out.println(Arrays.toString(array))
        );
    }

    /**
     * 原子数组：保护数组里面的元素：
     */
    private static void test2() {
        functionInterfaceDemo(
//                ()->new AtomicIntegerArray[10],
                ()->new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array,index)-> array.getAndIncrement(index),
                (array) -> System.out.println(array)
        );
    }



    //#region 函数式接口

    /**
     * 功能：测试普通数据和原子数组的安全性
     *    -参数1，提供数组、可以是线程不安全数组或线程安全数组 (无参有结果)
     *    -参数2，获取数组长度的方法（普通数组和原子数组获取数组长度是不一样的）
     *    -参数3，自增方法，回传 array, index
     *    -参数4，打印数组的方法
     */
    private static <T> void functionInterfaceDemo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
//            BiFunction<T,V,Integer> biFunction,
            BiConsumer<T, Integer> putConsumer,
//            Consumer<T> consumer,
            Consumer<T> printConsumer ) {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            // 每个线程对数组作 10000 次操作
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array, j % length);
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

    //#region 函数式接口之-> Supplier:结果提供者
    /**
     * 结果提供者：
     *  -函数每次调用不需要返回一个新的或者不同的结果
     * Represents a supplier of results.
     *
     * <p>There is no requirement that a new or distinct result be returned each
     * time the supplier is invoked.
     *
     * <p>This is a <a href="package-summary.html">functional interface</a>
     * whose functional method is {@link #get()}.
     *
     * @param <T> the type of results supplied by this supplier
     *
     * @since 1.8
     */

    /**
     * Supplier:无中生有，没有参数，返回一个结果 ()->结果
     */
    //#endregion

    //#region 函数式接口之-> Function (参数1,参数2)->结果
    /**
     *  function 函数   一个参数一个结果
     *   - 一个参数一个结果：(参数)->结果  ,
     *   - 多个参数一个结果：BiFunction (参数1,参数2)->结果
     */

    /**
     * 表示一个接受一个参数并产生结果的函数。
     * Represents a function that accepts one argument and produces a result.
     *
     * <p>This is a <a href="package-summary.html">functional interface</a>
     * whose functional method is {@link #apply(Object)}.
     *
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     *
     * @since 1.8
     */
    //#endregion

    //#region 函数式接口之-> BiConsumer 任意参数，无返回结果

    /**
     * consumer 消费者 一个参数没结果  (参数)->void,      BiConsumer (参数1,参数2)->
     */


    //#endregion

    //#endregion
}
    
    
    