package com.zlsoft.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: Account.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description
 * @createTime 2020年08月24日 11:29:00
 */
public class Account {
    public static void main(String[] args) {
        test2();
        test1();
    }

    private static void  test1() {
        IAccount accountUnsafe = new AccountUnsafe(10000);
        IAccount.demo(accountUnsafe);
    }

    private static void  test2() {
        IAccount accountUnsafe = new AccountSafe(10000);
        IAccount.demo(accountUnsafe);
    }
}

/**
 * 银行账号：
 */
interface  IAccount{

    /**
     * 获取账号余额
     */
    Integer getBalance();

    /**
     * 取款
     */

    void withDrawal(Integer amount);


    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(IAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withDrawal(10);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost: " + (end-start)/1000_000 + " ms");
    }

}

/**
 * 线程不安全的实现
 * Alt+enter:实现接口方法
 */
class AccountUnsafe implements IAccount {

    private Integer balance;

    /**
     * Alt+insert:初始化构造方法
     * @param balance
     */
    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    /**
     * 临界区代码
     * @param amount
     */
    @Override
    public void withDrawal(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }
}

/**
 *  线程安全：
 */
class AccountSafe implements IAccount {
    /**
     *
     */
    private AtomicInteger balance;

    public AccountSafe(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withDrawal(Integer amount) {
//        while (true) {
//            //工作线程中：
//            int prev = balance.get();
//            //工作线程中：
//            int next = prev - amount;
//            // 同步到主存中：比较并设置值
//            // compareAndSet，它的简称就是 CAS （也有 Compare And Swap 的说法），它必须是原子操作
//            if(balance.compareAndSet(prev,next)) {
//                break;
//            }
//        }
        /**
         * 优化：
         */
        balance.getAndAdd(-1 * amount);
        /**
         * 一元操作
         */
        balance.updateAndGet(param ->  param * 10);
    }
}


    
    
    