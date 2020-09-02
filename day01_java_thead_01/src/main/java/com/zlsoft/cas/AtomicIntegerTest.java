package com.zlsoft.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: AtomicIntegerTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 原子整数
 * @createTime 2020年08月26日 09:41:00
 */
@Slf4j(topic = "zl.atomicIntegerTest")
public class AtomicIntegerTest {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 加减乘除：原子操作
     */
    private static void test1() {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        /**
         * 先自增，在获取值 ++i 1
         */
        System.out.println(atomicInteger.incrementAndGet());
        /**
         * 获取值,在自增：i++; 1
         */
        System.out.println(atomicInteger.getAndIncrement());

        /**
         * 先获取值，在加：2
         */
        System.out.println(atomicInteger.getAndAdd(5));

        /**
         * 先加，在获取值：12
         */
        System.out.println(atomicInteger.addAndGet(5));


        /**
         * --i: 11
         */
        System.out.println(atomicInteger.decrementAndGet());

        /**
         * i--:11
         */
        System.out.println(atomicInteger.getAndDecrement());

    }

    /**
     * 测试复杂的原子操作：
     */
    private static void test2() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
//        函数式编程
        System.out.println(atomicInteger.getAndUpdate(value-> value *10));
        System.out.println(atomicInteger.updateAndGet(value-> value - 2));
        System.out.println(atomicInteger.get());
    }

    /**
     * 模拟原子复杂操作：
     */
    private static void test3() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        while (true) {
            int prev = atomicInteger.get();
            int next = prev * 10;
            if(atomicInteger.compareAndSet(prev,next)) {
                break;
            }
        }
        System.out.println(atomicInteger.get());
    }
    /**
     * 继续优化模拟一元原子操作：updateAndGet
     * unary:一元操作
     * binary：二元操作
     * @param atomicInteger
     * @param operator
     */
    private static int updateAndGet(AtomicInteger atomicInteger, IntUnaryOperator operator) {
        while (true) {
            int prev = atomicInteger.get();
            int next = operator.applyAsInt(prev);
            if(atomicInteger.compareAndSet(prev,next)) {
                return next;
            }
        }
    }

    /**
     * 测试：一元函数式编程
     */
    private static void test4() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(updateAndGet(atomicInteger, operand -> operand * 10));
        System.out.println(atomicInteger.get());
    }
}
    
    
    