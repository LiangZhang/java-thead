package com.zlsoft;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

// -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:BiasedLockingStartupDelay=0 -XX:+PrintFlagsFinal
//-XX:-UseBiasedLocking tid=0x000000001f173000  -XX:BiasedLockingStartupDelay=0 -XX:+TraceBiasedLocking
@Slf4j(topic = "c.TestBiased")
public class TestBiased {

    /*
    [t1] - 29	00000000 00000000 00000000 00000000 00011111 01000101 01101000 00000101
    [t2] - 29	00000000 00000000 00000000 00000000 00011111 01000101 11000001 00000101
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        no_lock();
    }

    private static void test5() throws InterruptedException {
        log.debug("begin");
        for (int i = 0; i < 6; i++) {
            Person d = new Person();
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            Thread.sleep(1000);
        }
    }

    static Thread t1, t2, t3;

    private static void test4() throws InterruptedException {
        Vector<Person> list = new Vector<>();

        int loopNumber = 39;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Person d = new Person();
                list.add(d);
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();

        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Person d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();

        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Person d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t3");
        t3.start();

        t3.join();
        log.debug(ClassLayout.parseInstance(new Person()).toPrintable());
    }

    private static void test3() throws InterruptedException {

        Vector<Person> list = new Vector<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Person d = new Person();
                list.add(d);
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1");
        t1.start();


        Thread t2 = new Thread(() -> {
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("===============> ");
            for (int i = 0; i < 30; i++) {
                Person d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t2");
        t2.start();
    }

    // 测试撤销偏向锁
    private static void test2() throws InterruptedException {

        Person d = new Person();
        Thread t1 = new Thread(() -> {
            synchronized (d) {
                log.debug(ClassLayout.parseInstance(d).toPrintable());
            }
            synchronized (TestBiased.class) {
                TestBiased.class.notify();
            }
        }, "t1");
        t1.start();


        Thread t2 = new Thread(() -> {
            synchronized (TestBiased.class) {
                try {
                    TestBiased.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                log.debug(ClassLayout.parseInstance(d).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable());
        }, "t2");
        t2.start();
    }

    // 测试偏向锁
    private static void test1() {
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        B b = new B();
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        C c = new C();
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        int[] aa = new int[0];
        System.out.println(ClassLayout.parseInstance(aa).toPrintable());
    }

    /**
     * 无锁状态
     */
    private static void no_lock(){
        Object object = new Object();
        System.out.println("hash: " + object.hashCode());
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }

}

class Person {

}
class A {}

class B {
    private long s;
}
class C {
    private int a;
    private long s;
}
