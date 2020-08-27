package com.zlsoft.cas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: DecimalAccount.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description: 原子引用：
 * @createTime 2020年08月26日 15:51:00
 */
public class DecimalAccount {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 测试浮点数：
     * 0.4
     * 0.19999999999999998
     * 0.03
     * 2.9999999999999996
     */
    private static void test1(){
        System.out.println(0.3 + 0.1);
        System.out.println(0.3-0.1);
        System.out.println(0.3 * 0.1);
        System.out.println(0.3/0.1);

        System.out.println(new BigDecimal("0.22"));
        BigDecimal decimal1 = new BigDecimal(1.111);
        BigDecimal decimal2 = new BigDecimal(2.222);
        System.out.println(decimal1);
        System.out.println(decimal2);
        System.out.println(decimal1.add(decimal2));

        BigDecimal decimal3 = new BigDecimal("1.111");
        BigDecimal decimal4 = new BigDecimal("2.222");
        System.out.println(decimal3);
        System.out.println(decimal4);
        System.out.println(decimal3.add(decimal4));
    }

    private static void test2() {
        DecimalAccountCas decimalAccountCas = new DecimalAccountCas(new BigDecimal("10000"));
        IDecimalAccount.demo(decimalAccountCas);
    }
}

/**
 * BigDecimal所创建的是对象，我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，而必须调用其相对应的方法。
 * 方法中的参数也必须是BigDecimal的对象。构造器是类的特殊方法，专门用来创建对象，特别是带有参数的对象。
 */

/**
 * 银行账号：
 */
interface  IDecimalAccount{

    /**
     * 获取账号余额
     */
    BigDecimal getBalance();

    /**
     * 取款
     */

    void withDrawal(BigDecimal amount);


    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(IDecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withDrawal(BigDecimal.TEN);
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
 * 原子引用
 */
class DecimalAccountCas implements IDecimalAccount {

    /**
     * 原子引用保护：
     *   -保护多个线程对一个对象的引用的安全使用
     */
    private AtomicReference<BigDecimal> balance;

    /**
     * 共享变量:多线程下读写
     */
//    private BigDecimal balance;

    public DecimalAccountCas(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance.get();
    }

    @Override
    public void withDrawal(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = amount.subtract(amount);
            if(balance.compareAndSet(prev,next)) {
                break;
            }
        }
    }
}



    
    
    