package com.zlsoft.service;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@Slf4j
//测试模式：测试方法平均执行时间
@BenchmarkMode(Mode.AverageTime)
//热身次数
@Warmup(iterations = 10)
//测试次数
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)// 输出结果的时间粒度为微秒
@State(Scope.Benchmark) // 每个测试_线程一个实例
public class FourthBenchmark {
    static int[] ARRAY = new int[10000_000_00];
    static {
        //Assigns the specified int value to each element of the specified array
        //  of ints.
        Arrays.fill(ARRAY,1);
    }


    @Benchmark
    public static int c() throws Exception {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 999999; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        new Thread(t1).start();
        return t1.get();
    }

    @Benchmark
    public static int d() throws Exception {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 333333; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        FutureTask<Integer> t2 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 333333; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        FutureTask<Integer> t3 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 333333; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        FutureTask<Integer> t4 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 333333; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        return t1.get() + t2.get() + t3.get() + t4.get();
    }

//    public static void main(String[] args) throws Exception {
//        c();
//        d();
//    }
}
