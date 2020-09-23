 *        -获取静态变量 INSTANCE，// -------------------------------------> 加入对 INSTANCE 变量的读屏障
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
 *       10: monitorenter                      //保证原子性、可见性
 *       11: getstatic     #2                  // Field INSTANCE:Lcom/zlsoft/doubleSingleton/Singleton;
 *       14: ifnonnull     27
 *       -如果为null,创建Singleton实例
 *       17: new           #3                  // class com/zlsoft/doubleSingleton/Singleton
 *       -复制了一份引用，
 *       20: dup
 *       -复制的一份引用调用Singleton实例构造方法
 *       21: invokespecial #4                  // Method "<init>":()V
 *       -另一个引用执行赋值方法，赋值给静态变量（INSTANCE = new Singleton();）,加入对 INSTANCE 变量的写屏障
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
 -------------------------------------------------------------------------------------------------------------------------------
 可见性
    -写屏障（sfence）保证在该屏障之前的 t1 对共享变量的改动，都同步到主存当中
    -而读屏障（lfence）保证在该屏障之后 t2 对共享变量的读取，加载的是主存中最新数据
 有序性
    -写屏障会确保指令重排序时，不会将写屏障之前的代码排在写屏障之后
    -读屏障会确保指令重排序时，不会将读屏障之后的代码排在读屏障之前
 更底层是读写变量时使用 lock 指令来多核 CPU 之间的可见性与有序性
 