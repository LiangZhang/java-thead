package com.zlsoft.doubleSingleton;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.doubleSingleton
 * @ClassName: Singleton.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 多线程下-Balking 单例模式 double-checked locking
 * @createTime 2020年08月06日 10:35:00
 */
//public final class Singleton {
//    private Singleton() { }
//    private static Singleton INSTANCE = null;
//    //
//
//    /**
//     * 多线程下运行：加synchronized
//     *  - 如果不加，多个线程有可能创建多个实例
//     * @return
//     */
//    public static synchronized Singleton getInstance() {
//        if(INSTANCE == null) {
//            INSTANCE = new Singleton();
//        }
//        return INSTANCE;
//    }
//}

/**
 * 改进1：
 */
//public final class Singleton {
//    private Singleton() { }
//    private static Singleton INSTANCE = null;
//    /**
//     * 改进1：synchronized加在static上，范围太大，
//     *  - 加在方法内部
//     * @return
//     */
//    public static Singleton getInstance() {
//        synchronized (Singleton.class) {
//            if(INSTANCE == null) {
//                INSTANCE = new Singleton();
//            }
//            return INSTANCE;
//        }
//    }
//}
/**
 * 改进2：double-checked locking
 *  - 当两个线程都进入了synchronized等待，如果在代码里面不判断INSTANCE是否存在，则可能多次创建实例对象
 *  - 懒惰实例化
 *  - 首次使用 getInstance() 才使用 synchronized 加锁，后续使用时无需加锁
 *
 *  java文件反编译字节码：
 *   -javac -encoding UTF-8 Singleton.java 编译成class文件
 *   -javap -c Singleton.class
 *
 *  字节码：
 *        -获取静态变量 INSTANCE
 *        0: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *        -如果静态变量不为null,则跳转到37
 *        3: ifnonnull     37
 *        -获得类对象
 *        6: ldc           #3                  // class com/zlsoft/doubleSingleton/Singleton
 *        -复制类对象的引用指针
 *        8: dup
 *        -临时存储一份，以后解锁用
 *        9: astore_0
 *        -底层创建monitor对象，执行到代码行（synchronized (Singleton.class)），判断是否有owner，如果有，进入阻塞队列，waitset中去等待
 *       10: monitorenter
 *       11: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       14: ifnonnull     27
 *       -如果为null,创建Singleton实例
 *       17: new           #3                  // class com/zlsoft/doubleSingleton/Singleton
 *       -复制了一份引用，
 *       20: dup
 *       -复制的一份引用调用Singleton实例构造方法
 *       21: invokespecial #4                  // Method "<init>":()V
 *       -另一个引用执行赋值方法，赋值给静态变量（INSTANCE = new Singleton();）
 *       24: putstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       -取得临时变量里面存储的类对象，（6:ldc 存储的）
 *       27: aload_0
 *       -解锁退出
 *       28: monitorexit
 *       -跳转到37指令
 *       29: goto          37
 *       32: astore_1
 *       33: aload_0
 *       34: monitorexit
 *       35: aload_1
 *       36: athrow
 *       -获得INSTANCE变量
 *       37: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       -返回INSTANCE对象
 *       40: areturn
 *
 */

/**
 * 问题：INSTANCE = new Singleton();
 * 17 new           #3                              表示创建对象，将对象引用入栈 // new Singleton
 * 20 dup                                           表示复制一份对象引用 // 引用地址
 * 21 invokespecial #4  // Method "<init>":()V      表示利用一个对象引用，调用无参构造方法
 * 24 putstatic     #2                              表示利用一个对象引用，赋值给 static INSTANCE
 *  也许jvm通过指令重排，先执行24，在执行21，如果有多个线程，t1,t2，那么在t1线程执行到0行字节码，取得t2刚生成的静态变量，但是这个是还未构造的实例对象，产生错误
 *  关键在于 0: getstatic 这行代码在 monitor 控制之外，它就像之前举例中不守规则的人，可以越过 monitor 读取 INSTANCE 变量的值
 *  这时 t1 还未完全将构造方法执行完毕，如果在构造方法中要执行很多初始化操作，那么 t2 拿到的是将是一个未初始化完毕的单例对
 * 解决方案：INSTANCE 使用 volatile 修饰即可，可以禁用指令重排
 */
//public final class Singleton {
//    private Singleton() { }
//    private static Singleton INSTANCE = null;
//    /**
//     * 改进1：synchronized加在static上，范围太大，
//     *  - 加在方法内部
//     * @return
//     */
//    public static Singleton getInstance() {
//        // 首次访问会同步，而之后的使用没有 synchronized
//        // 这里代码有隐含的，但很关键的一点：第一个 if 使用了 INSTANCE 变量，是在同步块之外但在多线程环境下，上面的代码是有问题的
//        if(INSTANCE == null) {
//            synchronized (Singleton.class) {
//                if(INSTANCE == null) {
//                    INSTANCE = new Singleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}


/**
 * 改进3 标准:用volatile禁止指令重排，实现多线程的double-checked locking
 */
public final class Singleton {
    private Singleton() { }

    /**
     * 实现惰性加载
     * 问题1：解释为什么要加 volatile ?
     * 答：防止指令重排，new Singleton() 也许先进行赋值操作， 在调用构造方法，
     *     如果先进行赋值操作给静态成员变量INSTANCE，那么其他线程取得的就是为调用构造方法的对象，出错
     */
    private volatile static Singleton INSTANCE = null;
    /**
     * 问题2：对比实现3, 说出这样做的意义？
     * 改进1：synchronized加在static上，范围太大，
     *  - 加在方法内部，可以加锁的次数
     * @return
     */
    public static Singleton getInstance() {
        // 实例没创建，才会进入内部的 synchronized代码块
        if(INSTANCE == null) {
            synchronized (Singleton.class) {
                /**
                 * 问题3：为什么还要在这里加为空判断, 之前不是判断过了吗
                 * 答：防止多线程重复创建单例对象，也许有其它线程已经创建实例，所以再判断一次,
                 *     意思当多个线程同时调用的时候，当有一个线程t1获得锁执行，但是INSTANCE还未赋值，
                 *     那么其他线程就在waiset等待，当t1线程结束释放锁,其他线程随机获得锁，
                 *     如果不判断null,则重复创建对象
                 */
                if(INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}

