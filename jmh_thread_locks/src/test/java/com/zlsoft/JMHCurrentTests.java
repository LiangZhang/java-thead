package com.zlsoft;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.glsoft.report.core
 * @ClassName: JMHTests.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年06月10日 12:25:00
 */

/**
 * @Fork
 * 　　表示需要测几轮，每轮都包括预热和正式测试
 * @Warmup
 * 　　进行benchmark前的预热, 因为JVM 的 JIT 机制会把执行频率高的函数编译成机器码，从而提高速度
 *
 * 　　iterations：预热的次数
 * 　　time：每次预热的时间，
 * 　　timeUnit：时间单位，默认是s，默认是sec。
 * 　　batchSize：批处理大小，每次操作调用几次方法(看做一次调用)。
 * @Mode
 * 　　Throughput：吞吐量，单位时间执行次数（ops/time）
 * 　　AverageTime：平均时间，每次操作的执行时间（time/op）
 * 　　SampleTime：随机取样，最后输出取样结果的分布
 * 　　SingleShotTime：每次迭代只运行一次，可以测试冷启动的性能，此时会禁用warmup
 * 　　All：所有都测一下
 *
 * @Benchmark
 * 　　表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 @Test 类似。
 *
 * @State
 * 　　State定义了一个类实例的生命周期
 * 　　由于JMH允许多线程同时执行测试，不同的选项含义如下
 * 　　Scope.Thread：默认的State，每个测试线程分配一个实例；
 * 　　Scope.Benchmark：所有测试线程共享一个实例，用于测试有状态实例在多线程共享下的性能；
 * 　　Scope.Group：每个线程组共享一个实例；
 * @Setup
 * 　　方法注解，会在执行 benchmark 之前被执行，正如其名，主要用于初始化。
 * @TearDown
 * 　　方法注解，与@Setup 相对的，会在所有 benchmark 执行结束以后执行，主要用于资源的回收等。
 *
 * @Threads
 * 　　每个fork进程使用多少条线程去执行你的测试方法，默认值是Runtime.getRuntime().availableProcessors()。
 *
 * @Level
 * 　　用于控制 @Setup，@TearDown 的调用时机，默认是 Level.Trial。
 * 　　Trial：每个benchmark方法前后；
 * 　　Iteration：每个benchmark方法每次迭代前后；
 * 　　Invocation：每个benchmark方法每次调用前后，谨慎使用，需留意javadoc注释；
 *
 * @Param
 * 　　成员注解，可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。
 * 　　@Param注解接收一个String数组，
 * 　　在@setup方法执行前转化为为对应的数据类型。多个@Param注解的成员之间是乘积关系，譬如有两个用@Param注解的字段，第一个有5个值，第二个字段有2个值，那么每个测试方法会跑5*2=10次。
 */

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;


@State(value = Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHCurrentTests {

    private ConfigurableApplicationContext context;
    static int x = 0;

//    private IChildTableMemoryManager childTableMemoryManager;


    @Test
    public void test() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHCurrentTests.class.getSimpleName())
                .mode(Mode.AverageTime)////测试取平均运行时间
                .warmupIterations(3)//预热测试：3 次
                .measurementIterations(3)//正式测试：3次
                .forks(1)//进行1轮基准测试
                .threads(1) // 1线程并发
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
//        context = SpringApplication.run(GlReportCoreApplication.class);
//        childTableMemoryManager = context.getBean(ChildTableMemoryManager.class);
    }

    @TearDown
    public void finish(){
        context.close();
    }


    /**
     * 对局部变量++，不加锁
     * @throws Exception
     */
    @Benchmark
    public void a() throws Exception {
        x++;
    }

    //对局部变量++，加锁
    @Benchmark
    // JIT  即时编译器
    public void b() throws Exception {
        Object o = new Object();
        synchronized (o) {
            x++;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("5");
    }


//    @Benchmark
//    public void testFastJSON(){
//        childTableMemoryManager.buildReportTable(719978773709066240L,true);
//    }
}

