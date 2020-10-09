package com.zlsoft.juc.aqs.stampedlock;

import java.util.concurrent.locks.StampedLock;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.juc.aqs.stampedlock
 * @ClassName: StampedLockTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020年10月09日 09:53:00
 * @Description StampedLock同步器使用测试：
 * 优点：读写锁，进一步优化读读锁
 *  - 乐观读，StampedLock 支持 tryOptimisticRead() 方法（乐观读），读取完毕后需要做一次 戳校验 如果校验通
 *    过，表示这期间确实没有写操作，数据可以安全使用，如果校验没通过，需要重新获取读锁，保证数据安全
 *    a. 读取的时候，未进行任何加锁，先进行一次戳校验。
 *    b. 如果戳校验失败（有写线程），才进行一次锁升级，升级成读锁，（加锁过程）
 * 缺点：
 *  -不支持可重入
 *  -不支持条件变量
 */
public class StampedLockTest {
    private StampedLock sl = new StampedLock();

}
    
    
    