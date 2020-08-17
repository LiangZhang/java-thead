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
 -----------------------------------------------------------------------------------------------------
 也许 jvm 会优化（指令重排）为：先执行 24（把对象赋值给静态变量INSTANCE），再执行 21（创建对象构造函数）
 关键在于 0: getstatic 这行代码在 monitor 控制之外，它就像之前举例中不守规则的人，可以越过 monitor 读取
 INSTANCE 变量的值
 这时 t1 还未完全将构造方法执行完毕，如果在构造方法中要执行很多初始化操作，那么 t2 拿到的是将是一个未初始化完毕的单例