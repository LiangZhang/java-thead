package com.zlsoft.threadPoolExecutor.fixedThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor.fixedThreadPool
 * @ClassName: mThreadTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月08日 17:41:00
 */
@Slf4j(topic = "zl.MultiThreadTest")
public class MultiThreadTest {
    public static void main(String[] args) {
        test1();
    }

    /**
     * 测试固定线程池的使用
     */
    private static void test1() {
        try {
            List<Map> shardsList = new ArrayList<>();
            Map map1 = new HashMap(2);
            map1.put("age",15);
            map1.put("name","张三");
            shardsList.add(map1);

            Map map2 = new HashMap(2);
            map2.put("age",27);
            map2.put("name","李四");
            shardsList.add(map2);

            Map map3 = new HashMap(2);
            map3.put("age",15);
            map3.put("name","王五");
            shardsList.add(map3);

            Map map4 = new HashMap(2);
            map4.put("age",15);
            map4.put("name","赵⑥");
            shardsList.add(map4);


            shardsList.add(map4);
            shardsList.add(map4);
            shardsList.add(map4);
            shardsList.add(map4);



            MultiThread<Map, MultiThreadResult> multiThread = new MultiThread<Map, MultiThreadResult>(shardsList) {
                @Override
                public MultiThreadResult outExecute(int currentThread, Map shards) {
                    MultiThreadResult result = new MultiThreadResult();
                    log.info("当前执行位置,{},人员姓名：{},年龄：{}",currentThread,shards.get("name"),shards.get("age"));
                    return result;
                }
            };
            List<MultiThreadResult> multiResult = multiThread.getResult();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
    
    