package com.zlsoft.n1;


/**
 *  字节码：
 *   ### Code:
 *          stack=2, locals=3, args_size=1
 *          0: getstatic #2 // <- 取得lock引用 （synchronized开始)
 *          3: dup   //复制一份这个lock对象引用
 *          4: astore_1 // lock引用 -> 存储到临时变量slot 1中，为了将来解锁用
 *          5: monitorenter // （执行synchronized语句）将 lock对象 MarkWord 置为 Monitor 指针
 *          6: getstatic #3 // <- i  取得counter成员变量，#3代表counter成员变量
 *          9: iconst_1 // 准备常数 1
 *          10: iadd // +1
 *          11: putstatic #3 // -> i
 *          //6~11指令执行counter++;
 *          14: aload_1 // <- 从slot 1中得到lock引用
 *          15: monitorexit // 将 lock对象 MarkWord 重置,把monitor指针，还原成hashcode, 唤醒 EntryList
 *          16: goto 24 //跳转到24行
 *          19: astore_2 // e -> slot 2
 *          20: aload_1 // <- lock引用
 *          21: monitorexit // 将 lock对象 MarkWord 重置, 唤醒 EntryList
 *          22: aload_2 // <- slot 2 (e)
 *          23: athrow // throw e
 *          24: return
 *      Exception table: //监控异常：
 *        from    to  target type
 *            6    16    19   any
 *           19    22    19   any
 *
 */

/**
 * 编译java类
 *    javac monitor.java
 * 查看类字节码：
 *    javap -c monitor
 */


/**
 *  Mark Word (64 bits)
 * 重量级锁：-lightweight Lock：- 　Monitor锁记录地址（ptr_to_heavyweight_monitor:62） + markword对象后两位 00
 */
public class monitor {
    static final Object lock = new Object();
    //保护的共享成员变量
    static int counter = 0;
    public static void main(String[] args) {

        //临界区代码：
        //每个synchronized关联一个monitor锁
        synchronized (lock) {
            counter++;
        }
    }
}

