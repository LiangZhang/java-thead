//package com.zlsoft;
//
///**
// * @version 1.0.0
// * @RESTful：Create-post Read-get update-put/path delete-delete
// * @package: com.glsoft.report.core
// * @ClassName: JMHTests.java
// * @author: L.Z QQ.191288065@qq.com
// * @Description 广力年报-年报基层框架API
// * @createTime 2020年06月10日 12:25:00
// */
//import org.junit.jupiter.api.Test;
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//
//@State(value = Scope.Thread)
//public class JMHSpringBootTests {
//
//    private ConfigurableApplicationContext context;
//
//
//    @Test
//    public void test() throws RunnerException {
//        Options options = new OptionsBuilder()
//                .include(JMHSpringBootTests.class.getSimpleName())
//                .warmupIterations(1)//预热做2轮
//                .measurementIterations(1)
//                .forks(10)
//                .build();
//        new Runner(options).run();
//    }
//
//
//
//    @Setup
//    public void init()  {
////        JSONObject object = new JSONObject();
////        object.put("requestId", UUID.randomUUID().toString());
////        object.put("requestName", "test");
////        object.put("requestData", "122345");
////        object.put("requestType", "POST");
////        reqStr = object.toString();
////        System.err.println("init....");
//        context = SpringApplication.run(GlReportCoreApplication.class);
//        childTableMemoryManager = context.getBean(ChildTableMemoryManager.class);
//    }
//
//    @TearDown
//    public void finish(){
//        context.close();
//    }
//
//    @Benchmark
//    public void testFastJSON(){
//        childTableMemoryManager.buildReportTable(719978773709066240L,true);
//    }
//}