package com.zlsoft.n2;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class SecondBenchmark {
    /**
     * @Param
     * @Param 可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。
     */
    @Param({"10000", "100000", "1000000"})
    private int length;

    private int[] numbers;
    private Calculator singleThreadCalc;
    private Calculator multiThreadCalc;

    @Test
    public void secondTest() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SecondBenchmark.class.getSimpleName())
                .forks(2)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public long singleThreadBench() {
        return singleThreadCalc.sum(numbers);
    }

    @Benchmark
    public long multiThreadBench() {
        return multiThreadCalc.sum(numbers);
    }

    /**
     * @Setup
     * @Setup 会在执行 benchmark 之前被执行，正如其名，主要用于初始化
     */
    @Setup
    public void prepare() {
        numbers = IntStream.rangeClosed(1, length).toArray();
        singleThreadCalc = new SinglethreadCalculator();
        multiThreadCalc = new MultithreadCalculator(Runtime.getRuntime().availableProcessors());
    }

    /**
     * @TearDown
     * @TearDown 和 @Setup 相对的，会在所有 benchmark 执行结束以后执行，主要用于资源的回收等。
     */
    @TearDown
    public void shutdown() {
        singleThreadCalc.shutdown();
        multiThreadCalc.shutdown();
    }

    /**
     * 还有一些 JMH 的常用选项没有提及的，简单地在此介绍一下
     *
     * CompilerControl
     * 控制 compiler 的行为，例如强制 inline，不允许编译等。
     *
     * Group
     * 可以把多个 benchmark 定义为同一个 group，则它们会被同时执行，主要用于测试多个相互之间存在影响的方法。
     *
     * Level
     * 用于控制 @Setup，@TearDown 的调用时机，默认是 Level.Trial，即benchmark开始前和结束后。
     *
     * Profiler
     * JMH 支持一些 profiler，可以显示等待时间和运行时间比，热点函数等。
     */
}
