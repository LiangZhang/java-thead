package com.zlsoft.n0;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

/**
 * 正向循环
 */
public class ForwardCountPerf {
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void count() {
        for (int i = 0; i < 1_000_000; i++) {
//            System.out.println(i);
        }
    }
}
