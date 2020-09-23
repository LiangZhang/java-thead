package com.zlsoft.threadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.threadPoolExecutor
 * @ClassName: sourceCode.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年09月07日 17:35:00
 */
@Slf4j(topic = "zl.threadPoolExecutor")
public class sourceCode {

    /**
     *
     * @param rs:runState:线程状态
     * @param wc:workCount:线程数
     * @return rs | wc 进行位或
     */
//    private static int ctlOf(int rs, int wc) { return rs | wc; }

    /**
     * Transitions runState to given target, or leaves it alone if
     * already at least the given target.
     *
     * @param targetState the desired state, either SHUTDOWN or STOP
     *        (but not TIDYING or TERMINATED -- use tryTerminate for that)
     */
//    private void advanceRunState(int targetState) {
//        for (;;) {
//            int c = ctl.get();
//            if (runStateAtLeast(c, targetState) ||
//                    //一次cas操作
//                    ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))))
//                break;
//        }
//    }


    /**
     *
     * @param corePoolSize the number of threads to keep in the pool, evenif they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param corePoolSize 线程池中的核心线程数目 (最多保留的线程数)，无论他们是否是空闲的，除非设置了线程超时时间
     *
     *
     * @param maximumPoolSize：最大线程：核心线程+救急线程数
     *                       救急线程是配合有界队列来实现
     *
     *
     * @param keepAliveTime when the number of threads is greater than
     *        the core, this is the maximum time that excess idle threads
     *        will wait for new tasks before terminating.
     *  当线程数大于核心时，这是多余的空闲线程在终止之前等待新任务的最长时间。
     * @param keepAliveTime：生存时间 - 针对救急线程
     * @param unit
     * @param workQueue：阻塞队列,当阻塞队列+核心线程用完，在来任务进入救急线程，如果救急线程还不够，就进入拒绝策略，handler
     * @param threadFactory
     * @param handler:拒绝策略
     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler) {
//        if (corePoolSize < 0 ||
//                maximumPoolSize <= 0 ||
//                maximumPoolSize < corePoolSize ||
//                keepAliveTime < 0)
//            throw new IllegalArgumentException();
//        if (workQueue == null || threadFactory == null || handler == null)
//            throw new NullPointerException();
//        this.acc = System.getSecurityManager() == null ?
//                null :
//                AccessController.getContext();
//        this.corePoolSize = corePoolSize;
//        this.maximumPoolSize = maximumPoolSize;
//        this.workQueue = workQueue;
//        this.keepAliveTime = unit.toNanos(keepAliveTime);
//        this.threadFactory = threadFactory;
//        this.handler = handler;
//    }

    public static void main(String[] args) {
        Integer i = Runtime.getRuntime().availableProcessors();
        System.out.println("可用于Java虚拟机的处理器数量：" + i.toString());
        log.debug("可用于Java虚拟机的处理器数量：{}",Runtime.getRuntime().availableProcessors());


        //有任务调度的线程池
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

        //线程池
        ThreadPoolExecutor threadPoolExecutor;
        //固定大小线程池：
        ExecutorService executorService = Executors.newFixedThreadPool(5);
    }

    private static void test1() {

    }

}
    
    
    