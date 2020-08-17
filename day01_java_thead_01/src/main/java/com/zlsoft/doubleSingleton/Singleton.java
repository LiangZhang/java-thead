package com.zlsoft.doubleSingleton;

/**
 * @version 1.0.0
 * @RESTful��Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.doubleSingleton
 * @ClassName: Singleton.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description ���߳���-Balking ����ģʽ double-checked locking
 * @createTime 2020��08��06�� 10:35:00
 */
//public final class Singleton {
//    private Singleton() { }
//    private static Singleton INSTANCE = null;
//    //
//
//    /**
//     * ���߳������У���synchronized
//     *  - ������ӣ�����߳��п��ܴ������ʵ��
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
 * �Ľ�1��
 */
//public final class Singleton {
//    private Singleton() { }
//    private static Singleton INSTANCE = null;
//    /**
//     * �Ľ�1��synchronized����static�ϣ���Χ̫��
//     *  - ���ڷ����ڲ�
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
 * �Ľ�2��double-checked locking
 *  - �������̶߳�������synchronized�ȴ�������ڴ������治�ж�INSTANCE�Ƿ���ڣ�����ܶ�δ���ʵ������
 *  - ����ʵ����
 *  - �״�ʹ�� getInstance() ��ʹ�� synchronized ����������ʹ��ʱ�������
 *
 *  java�ļ��������ֽ��룺
 *   -javac -encoding UTF-8 Singleton.java �����class�ļ�
 *   -javap -c Singleton.class
 *
 *  �ֽ��룺
 *        -��ȡ��̬���� INSTANCE
 *        0: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *        -�����̬������Ϊnull,����ת��37
 *        3: ifnonnull     37
 *        -��������
 *        6: ldc           #3                  // class com/zlsoft/doubleSingleton/Singleton
 *        -��������������ָ��
 *        8: dup
 *        -��ʱ�洢һ�ݣ��Ժ������
 *        9: astore_0
 *        -�ײ㴴��monitor����ִ�е������У�synchronized (Singleton.class)�����ж��Ƿ���owner������У������������У�waitset��ȥ�ȴ�
 *       10: monitorenter
 *       11: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       14: ifnonnull     27
 *       -���Ϊnull,����Singletonʵ��
 *       17: new           #3                  // class com/zlsoft/doubleSingleton/Singleton
 *       -������һ�����ã�
 *       20: dup
 *       -���Ƶ�һ�����õ���Singletonʵ�����췽��
 *       21: invokespecial #4                  // Method "<init>":()V
 *       -��һ������ִ�и�ֵ��������ֵ����̬������INSTANCE = new Singleton();��
 *       24: putstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       -ȡ����ʱ��������洢������󣬣�6:ldc �洢�ģ�
 *       27: aload_0
 *       -�����˳�
 *       28: monitorexit
 *       -��ת��37ָ��
 *       29: goto          37
 *       32: astore_1
 *       33: aload_0
 *       34: monitorexit
 *       35: aload_1
 *       36: athrow
 *       -���INSTANCE����
 *       37: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       -����INSTANCE����
 *       40: areturn
 *
 */

/**
 * ���⣺INSTANCE = new Singleton();
 * 17 new           #3                              ��ʾ�������󣬽�����������ջ // new Singleton
 * 20 dup                                           ��ʾ����һ�ݶ������� // ���õ�ַ
 * 21 invokespecial #4  // Method "<init>":()V      ��ʾ����һ���������ã������޲ι��췽��
 * 24 putstatic     #2                              ��ʾ����һ���������ã���ֵ�� static INSTANCE
 *  Ҳ��jvmͨ��ָ�����ţ���ִ��24����ִ��21������ж���̣߳�t1,t2����ô��t1�߳�ִ�е�0���ֽ��룬ȡ��t2�����ɵľ�̬��������������ǻ�δ�����ʵ�����󣬲�������
 *  �ؼ����� 0: getstatic ���д����� monitor ����֮�⣬������֮ǰ�����в��ع�����ˣ�����Խ�� monitor ��ȡ INSTANCE ������ֵ
 *  ��ʱ t1 ��δ��ȫ�����췽��ִ����ϣ�����ڹ��췽����Ҫִ�кܶ��ʼ����������ô t2 �õ����ǽ���һ��δ��ʼ����ϵĵ�����
 * ���������INSTANCE ʹ�� volatile ���μ��ɣ����Խ���ָ������
 */
//public final class Singleton {
//    private Singleton() { }
//    private static Singleton INSTANCE = null;
//    /**
//     * �Ľ�1��synchronized����static�ϣ���Χ̫��
//     *  - ���ڷ����ڲ�
//     * @return
//     */
//    public static Singleton getInstance() {
//        // �״η��ʻ�ͬ������֮���ʹ��û�� synchronized
//        // ��������������ģ����ܹؼ���һ�㣺��һ�� if ʹ���� INSTANCE ����������ͬ����֮�⵫�ڶ��̻߳����£�����Ĵ������������
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
 * �Ľ�3 ��׼:��volatile��ָֹ�����ţ�ʵ�ֶ��̵߳�double-checked locking
 */
public final class Singleton {
    private Singleton() { }

    /**
     * ʵ�ֶ��Լ���
     * ����1������ΪʲôҪ�� volatile ?
     * �𣺷�ָֹ�����ţ�new Singleton() Ҳ���Ƚ��и�ֵ������ �ڵ��ù��췽����
     *     ����Ƚ��и�ֵ��������̬��Ա����INSTANCE����ô�����߳�ȡ�õľ���Ϊ���ù��췽���Ķ��󣬳���
     */
    private volatile static Singleton INSTANCE = null;
    /**
     * ����2���Ա�ʵ��3, ˵�������������壿
     * �Ľ�1��synchronized����static�ϣ���Χ̫��
     *  - ���ڷ����ڲ������Լ����Ĵ���
     * @return
     */
    public static Singleton getInstance() {
        // ʵ��û�������Ż�����ڲ��� synchronized�����
        if(INSTANCE == null) {
            synchronized (Singleton.class) {
                /**
                 * ����3��Ϊʲô��Ҫ�������Ϊ���ж�, ֮ǰ�����жϹ�����
                 * �𣺷�ֹ���߳��ظ�������������Ҳ���������߳��Ѿ�����ʵ�����������ж�һ��,
                 *     ��˼������߳�ͬʱ���õ�ʱ�򣬵���һ���߳�t1�����ִ�У�����INSTANCE��δ��ֵ��
                 *     ��ô�����߳̾���waiset�ȴ�����t1�߳̽����ͷ���,�����߳�����������
                 *     ������ж�null,���ظ���������
                 */
                if(INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}

