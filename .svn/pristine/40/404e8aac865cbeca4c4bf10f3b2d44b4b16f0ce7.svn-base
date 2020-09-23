package com.zlsoft.classLiterals;

import lombok.extern.slf4j.Slf4j;

//核心业务代码
@Slf4j(topic = "c.HotProduct")
public class HotProduct {
    public static void main(String[] args) {
        test5();
    }
    private static void test5() {
        HotProductService service= new HotProductService();
        ThreadA5 a=new ThreadA5(service);
        a.start();
        ThreadB5 b=new ThreadB5(service);
        b.start();
    }
}

@Slf4j(topic = "c.HotProduct")
class HotProductService {
    //业务A
    public  void methodA(){
        try {
            synchronized (HotProductService.class){
                log.debug("HotProductService: static methodA begin Name:" + Thread.currentThread().getName() + " times:" + System.currentTimeMillis());
                Thread.sleep(2000);
                log.debug("HotProductService: static methodA end   Name:"+Thread.currentThread().getName()+" times:"+System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //业务B
    public  void methodB(){
        synchronized (HotProductService.class){
            log.debug("HotProductService: static methodB begin Name:"+Thread.currentThread().getName()+" times:"+System.currentTimeMillis());
            log.debug("HotProductService: static methodB end   Name:"+Thread.currentThread().getName()+" times:"+System.currentTimeMillis());
        }
    }
}

//A线程
class ThreadA5 extends Thread{

    private  HotProductService mHotProductService;

    public ThreadA5(HotProductService hotProductService){
        mHotProductService = hotProductService;
    }
    @Override
    public void run() {
        super.run();
        mHotProductService.methodA();
    }
}

//B线程
class ThreadB5 extends Thread {

    private  HotProductService mHotProductService;

    public ThreadB5(HotProductService hotProductService){
        mHotProductService = hotProductService;
    }
    @Override
    public void run() {
        super.run();
        mHotProductService.methodB();
    }
}