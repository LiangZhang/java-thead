package com.zlsoft.jol;

import com.zlsoft.core.TranslationClassLayout;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        test6();
    }

    static Thread t1, t2, t3;

    /**
     * JDK8以后默认开启-XX:CompressedOops选项，所以上述为开启指针压缩的结果
     * 查看一个对象的对象布局:JOL 返回的默认是从低位到高位。
     *  1.如果开启了偏向锁（默认开启），那么对象创建后，markword 值为 0x05 即最后 3 位为 101，这时它的thread、epoch、age 都为 0
     *  2.偏向锁是默认是延迟的，不会在程序启动时立即生效
     */
    private static void test1() {
        //当对象在Main线程调用创建吗，偏向锁有延迟，还未打开，所有最后三位 001
        //如果直接开启偏向锁：-XX:BiasedLockingStartupDelay=0
        Person d = new Person();
        TranslationClassLayout translationClassLayout = new TranslationClassLayout(d);
        log.debug("加锁前：" + translationClassLayout.toMarkWord());
        synchronized (d) {
            log.debug("加锁中：" + translationClassLayout.toMarkWord());
        }
        log.debug("释放锁：" + translationClassLayout.toMarkWord());
//        log.debug(translationClassLayout.toPrintable());

//        ClassLayout classLayout = ClassLayout.parseInstance(d);
//        String s = classLayout.toPrintable();
//        String[] split = s.split("\r\n");
//
//
//
//        //偏向锁延迟开启，默认markword  00000001 00000000 00000000 00000000
//        log.debug(ClassLayout.parseInstance(d).toPrintable());
    }

    /**
     * 启用偏向锁，表示可以加偏向锁,目前还没有加锁
     */
    private static void test2() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //延迟10秒创建对象，程序默认是开启偏移锁的，所有最后三位 101
        //0 4 (object header)  05 00 00 00 (00000101 00000000 00000000 00000000) (5)
        log.debug(ClassLayout.parseInstance(new Person()).toPrintable());
    }

    /**
     * 用synchronized 加锁
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException {
        //自动开启偏向锁，禁用延迟
        //-XX:BiasedLockingStartupDelay=0
        //禁用偏向锁
        //-XX:-UseBiasedLocking
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //加锁前：声明一个对象：启用偏向锁
        Person d = new Person();
        log.debug(ClassLayout.parseInstance(d).toPrintable());

        //加锁：
        synchronized (d) {
            //  00000010 01000001 10111000 00000101
            log.debug(ClassLayout.parseInstance(d).toPrintable());
        }

        //加锁后：
        log.debug(ClassLayout.parseInstance(d).toPrintable());
    }

    /**
     * 用synchronized 加锁
     * 调用对象的hashcode可以撤销该对象的偏向锁
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        //自动开启偏向锁，禁用延迟
        //-XX:BiasedLockingStartupDelay=0
        //禁用偏向锁
        //-XX:-UseBiasedLocking
        //加锁前：声明一个对象：启用偏向锁
        Person d = new Person();
        log.debug("对象默认开启偏向锁：Biased_lock:1 + lock:2(101)" + ClassLayout.parseInstance(d).toPrintable());
        /**
         * ### 调用了对象的 hashCode，但偏向锁的对象 MarkWord 中存储的是线程 id，如果调用 hashCode 会导致偏向锁被撤销
         *   --轻量级锁会在锁记录中记录 hashCode
         *   --重量级锁会在 Monitor 中记录 hashCode
         */
        d.hashCode();
        log.debug("调用hashCode，撤销偏向锁,无锁状态：Biased_lock:1 + lock:2(001)" + ClassLayout.parseInstance(d).toPrintable());

        //加锁：
        synchronized (d) {
            //  00000010 01000001 10111000 00000101
            log.debug("加轻量级锁：lock:2 (00)" + ClassLayout.parseInstance(d).toPrintable());
        }

        //加锁后：
        log.debug("锁释放：无锁状态：Biased_lock:1 + lock:2(001)" + ClassLayout.parseInstance(d).toPrintable());
    }

    /**
     * 批量重偏向:阈值20，可以对象偏向其他线程
     *    -如果对象虽然被多个线程访问，但没有竞争，这时偏向了线程 T1 的对象仍有机会重新偏向 T2，重偏向会重置对象的 Thread ID
     *    -当撤销偏向锁阈值超过 20 次后，jvm 会这样觉得，我是不是偏向错了呢，于是会在给这些对象加锁时重新偏向至加锁线程
     * @throws InterruptedException
     */
    private static void test5() throws InterruptedException {
        // public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
        //    public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
        List list1 = new ArrayList();
        //线程安全：
        Vector<Person> list = new Vector<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 21; i++) {
                Person d = new Person();
                list.add(d);
                //这30个对象都偏向同一个线程 t1
                synchronized (d) {
                    if(i == 20) log.debug("====t1线程中给对象加锁（偏向锁）："+ i + "\t" + ClassLayout.parseInstance(d).toPrintable());
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
            log.debug("END===============> ");
            //线程t2，对30个对象加锁
            for (int i = 0; i < 21; i++) {
                Person d = list.get(i);
                if(i> 17) log.debug("t2线程中当前第" + i + "个对象加锁前MarkWord：\t" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    //1、当阈值未到20的时候，对象撤销对象的t1偏向锁，升级成轻量级锁
                    //2、撤销偏向锁阈值超过 20 次后，jvm 会这样觉得，我是不是偏向错了呢，于是会在给这些剩下对象加锁时重新设置偏向至t2线程，不会进行（撤销偏向锁，设置轻量级锁）过程
                    if(i> 17) log.debug("t2线程中当前第" + i + "个对象加锁中MarkWord：\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                if(i> 17) log.debug("t2线程释放锁（解锁）中目前对象" + i + "的markword\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t2");
        t2.start();
    }

    /**
     *批量撤销：阈值40，新建的对象也是不可偏向
     * 当撤销偏向锁阈值超过 40 次后，jvm 会这样觉得，自己确实偏向错了，根本就不该偏向。于是整个类的所有对象
     * 都会变为不可偏向的，新建的对象也是不可偏向的
     * @throws InterruptedException
     */
    private static void test6() throws InterruptedException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Vector<Person> list = new Vector<>();

        int loopNumber = 39;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Person d = new Person();
                log.info("加锁前，线程t1,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                if(i == 38)  log.debug("线程t1,对象"+ i +"默认开启偏向锁，加锁前：Biased_lock:1 + lock:2(101)" + ClassLayout.parseInstance(d).toPrintable());
                list.add(d);
                synchronized (d) {
                    log.info("加锁中，线程t1,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                      if(i == 38) log.debug("t1线程加锁完成");
//                    if(i == 38) log.debug("线程t1,对象"+ i +"执行synchronized，加锁中：" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.info("加锁后，线程t1,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                if(i == 38) log.debug("线程t1,对象"+ i +"解锁中：" + ClassLayout.parseInstance(d).toPrintable());
            }
            //线程同步原语:
            //https://blog.csdn.net/hengyunabc/article/details/28126139
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();
        //t1线程里面synchronized为前39个对象偏向锁为t1，
        //t2线程里面synchronized加锁的时候，前20个对象撤销偏向锁，加轻量级锁，后20个对象批量重偏向设置偏向t2线程的偏向锁
        //t3线程里面synchronized加锁的时候，在40个对象后，生成对象都是无锁状态,不进行任何偏向

        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Person d = list.get(i);
                log.info("加锁前，线程t2,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
                synchronized (d) {
                    log.info("加锁中，线程t2,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                    if(i == 35)  log.debug("线程t2,对象"+ i +"再次执行synchronized，加锁中markword：" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.info("加锁后，线程t2,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                if(i == 35)  log.debug("线程t2,对象"+ i +"解锁后Markword：" + ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();

        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Person d = list.get(i);
                log.info("加锁前，线程t3,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                if(i == 35)  log.debug("线程t3,对象"+ i +"，加锁前markword" + ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    log.info("加锁中，线程t3,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                    if(i == 35)  log.debug("线程t3,对象"+ i +"再次执行synchronized，加锁中markword：" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.info("加锁后，线程t3,对象"+ i + ":"+ new TranslationClassLayout(d).toMarkWord());
//                if(i == 35)  log.debug("线程t3,对象"+ i +"解锁后Markword：" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t3");
        t3.start();

        t3.join();
        log.info("新创建对象:"+ new TranslationClassLayout(new Person()).toMarkWord());
//        log.debug(ClassLayout.parseInstance(new Person()).toPrintable());
    }

    private static void test14() {
        System.out.println(ClassLayout.parseClass(Object.class).toPrintable());
        System.out.println(ClassLayout.parseInstance(true).toPrintable());
        System.out.println(ClassLayout.parseInstance(1).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Integer(1)).toPrintable());
    }
}

class Person {
    private String a;
    private Boolean b;
}
