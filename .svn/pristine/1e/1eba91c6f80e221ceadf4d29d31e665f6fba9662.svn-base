package com.zlsoft.n0;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.glsoft.report.core
 * @ClassName: JMHTests.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年06月10日 12:25:00
 */
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * ### BenchmarkMode
 *
 * Mode 表示 JMH 进行 Benchmark 时所使用的模式。通常是测量的维度不同，或是测量的方式不同。目前 JMH 共有四种模式：
 *
 * Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”。
 * AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”。
 * SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
 * SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。
 */

/**
 * ### Iteration
 * Iteration 是 JMH 进行测试的最小单位。在大部分模式下，一次 iteration 代表的是一秒，
 * JMH 会在这一秒内不断调用需要 benchmark 的方法，然后根据模式对其采样，计算吞吐量，计算平均执行时间等
 */

/**
 * ### Warmup
 * Warmup 是指在实际进行 benchmark 前先进行预热的行为。
 * 为什么需要预热？因为 JVM 的 JIT 机制的存在，如果某个函数被调用多次之后，
 * JVM 会尝试将其编译成为机器码从而提高执行速度。所以为了让 benchmark
 * 的结果更加接近真实情况就需要进行预热。
 */

/**
 *  ### 注解
 *
 * 现在来解释一下上面例子中使用到的注解，其实很多注解的意义完全可以望文生义 :)
 *
 * @Benchmark
 * 表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 @Test 类似。
 *
 * @Mode
 * Mode 如之前所说，表示 JMH 进行 Benchmark 时所使用的模式。
 *
 * @State
 * State 用于声明某个类是一个“状态”，然后接受一个 Scope 参数用来表示该状态的共享范围。因为很多 benchmark 会需要一些表示状态的类，JMH 允许你把这些类以依赖注入的方式注入到 benchmark 函数里。Scope 主要分为两种。
 *
 * Thread: 该状态为每个线程独享。
 * Benchmark: 该状态在所有线程间共享。
 * 关于State的用法，官方的 code sample 里有比较好的例子。
 *
 * @OutputTimeUnit
 * benchmark 结果所使用的时间单位。
 */


//测试模式：测试方法平均执行时间
@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHCurrentTests {

    private ConfigurableApplicationContext context;
    private int[] ARRAY = new int[90];


    /**
     * ### 启动选项
     * include
     * benchmark 所在的类的名字，注意这里是使用正则表达式对所有类进行匹配的。
     *
     * fork
     * 进行 fork 的次数。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试。
     *
     * warmupIterations
     * 预热的迭代次数。
     *
     * measurementIterations
     * 实际测量的迭代次数。
     * @throws RunnerException
     */
    @Test
    public void test() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHCurrentTests.class.getSimpleName())
                .warmupIterations(2)//预热做2轮
                .measurementIterations(2)
                .forks(1)
                .build();
        new Runner(options).run();
    }



    @Setup
    public void init()  {
//        JSONObject object = new JSONObject();
//        object.put("requestId", UUID.randomUUID().toString());
//        object.put("requestName", "test");
//        object.put("requestData", "122345");
//        object.put("requestType", "POST");
//        reqStr = object.toString();
//        System.err.println("init....");
//        context = SpringApplication.run(JmhApplication.class);
//        childTableMemoryManager = context.getBean(ChildTableMemoryManager.class);
        Arrays.fill(ARRAY,1);
    }

//    @TearDown
//    public void finish(){
//        context.close();
//    }


    @Benchmark
    public int c() throws Exception {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 90; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        new Thread(t1).start();
        return t1.get();
    }

    @Benchmark
    public int d() throws Exception {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 30; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        FutureTask<Integer> t2 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 30; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        FutureTask<Integer> t3 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 30; i++) {
                sum += array[0 + i];
            }
            return sum;
        });

        FutureTask<Integer> t4 = new FutureTask<>(()->{
            int sum = 0;
            for (int i = 0; i < 30; i++) {
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
}