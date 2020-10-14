package com.zlsoft.threadPoolExecutor.fixedThreadPool;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.core
 * @ClassName: MultiThread.java
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020年06月08日 15:25:00
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @param <H> 为被处理的数据类型,
 * @param <T>返回数据类型
 */
public abstract class MultiThread<H,T>{
    /**
     * 线程池
     */
    private final ExecutorService exec;
    /**
     * 无界队列：
     */
    private final BlockingQueue<Future<T>> queue = new LinkedBlockingQueue<>();
    /**
     * 启动门，当所有线程就绪时调用countDown
     */
    private final CountDownLatch startLock = new CountDownLatch(1);
    /**
     * 结束门
     */
    private final CountDownLatch endLock;
    /**
     * 被处理的数据
     */
    private final List<H> listData;


    /** 重订
     * @param list list.size()为多少个线程处理，list里面的H为被处理的数据
     */
    public MultiThread(List<H> list){
        if(list!=null && list.size()>0){
            this.listData = list;
            //创建线程池，线程池共有nThread个线程
            //线程数 = 核数 * 期望 CPU 利用率 * 总时间(CPU计算时间+等待时间) / CPU 计算时间
            int coreCount = Runtime.getRuntime().availableProcessors() * 10 - 2;
            exec = Executors.newFixedThreadPool(coreCount);
            //设置结束门计数器，当一个线程结束时调用countDown
            endLock = new CountDownLatch(list.size());
        }else{
            listData = null;
            exec = null;
            endLock = null;
        }
    }

    /**
     *
     * @return 获取每个线程处理结速的数组
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public List<T> getResult() throws InterruptedException, ExecutionException{
        List<T> resultList = new ArrayList<>();
        if(listData !=null && listData.size()>0){
            //线程数量
            int nThread = listData.size();
            for(int i = 0; i < nThread; i++){
                H data = listData.get(i);

                //线程池执行任务
                // exec.execute(()->{});

                //将任务提交到线程池
                Future<T> future = exec.submit(new Task(i,data){
                    @Override
                    public T execute(int currentThread,H data) {
                        return outExecute(currentThread,data);
                    }
                });
                //将Future实例添加至队列
                queue.add(future);
            }
            //所有任务添加完毕，启动门计数器减1，这时计数器为0，所有添加的任务开始执行
            startLock.countDown();
            //主线程阻塞，直到所有线endLock.await();程执行完成

            for(Future<T> future : queue) {
                resultList.add(future.get());
            }

            //关闭线程池
            exec.shutdown();
        }
        return resultList;
    }
    /**
     * 每一个线程执行的功能，需要调用者来实现
     * @param currentThread 线程号
     * @param data 每个线程被处理的数据
     * @return T返回对象
     */
    public abstract T outExecute(int currentThread,H data);

    /**
     * 线程类
     */
    private abstract class Task implements Callable<T>{
        //当前线程号
        private int currentThread;
        private H data;
        public Task(int currentThread,H data){
            this.currentThread=currentThread;
            this.data=data;
        }
        @Override
        public T call() throws Exception {
            //线程启动后调用await，当前线程阻塞，只有启动门计数器为0时当前线程才会往下执行
            startLock.await();
            T t =null;
            try{
                t = execute(currentThread,data);
            }finally{
                //线程执行完毕，结束门计数器减1
                endLock.countDown();
            }
            return t;
        }

        /**
         * 每一个线程执行的功能
         * @param currentThread 线程号
         * @param data 每个线程被处理的数据
         * @return T返回对象
         */
        public abstract T execute(int currentThread,H data);
    }
}