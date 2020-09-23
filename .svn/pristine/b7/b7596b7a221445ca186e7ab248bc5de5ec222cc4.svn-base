package com.zlsoft.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "c.TestBiased")
public class JolTest {
    /**
     * Object object = new Object(); 共占用16个字节(开启压缩), 不开启压缩也是占用16个字节
     */
    public static void main(String[] args) {
        Person1 object = new Person1();
        /**
         * java.lang.Object object internals:
         * OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         * -- markword (共8个字节 : 此处占用4个)
         * 0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         * -- markword (共8个字节 : 此处占用4个)
         * 4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         * -- class pointer (类型执行) 占用4个字节(开启压缩后, 如若不开启压缩则占用8个字节) (markword + class pointer 为对象头)
         * 8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
         * -- instance data (实例数据) 无数据(0个字节, 不进行展示)
         * -- padding (对齐) 4 + 4 + 4 = 12 不能被8整除, 则补充4个字节 (如果class pointer不开启压缩, 则4 + 4 + 8 = 16 能被8整除, 则无需对齐)
         * 12     4        (loss due to the next object alignment)
         * -- (共占用16字节)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
//        System.err.println(ClassLayout.parseInstance(object).toPrintable());
        log.info(ClassLayout.parseInstance(object).toPrintable());
        synchronized (object) {
            /**
             * 锁升级 : new(无锁) - 偏向锁(无锁竞争) - 轻量级锁 (无锁、自旋锁、自适应锁)CAS(底层 : lock cmpexchg) - 重量级锁
             * java.lang.Object object internals:
             * OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
             * -- markword (共8个字节 : 此处占用4个)
             * 0     4        (object header)                           70 f4 6e 02 (01110000 11110100 01101110 00000010) (40825968)
             * -- markword (共8个字节 : 此处占用4个)
             * 4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
             * -- class pointer (类型执行) 占用4个字节(开启压缩后, 如若不开启压缩则占用8个字节) (markword + class pointer 为对象头)
             * 8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
             * -- instance data (实例数据) 无数据(0个字节, 不进行展示)
             * -- padding (对齐) 4 + 4 + 4 = 12 不能被8整除, 则补充4个字节 (如果class pointer不开启压缩, 则4 + 4 + 8 = 16 能被8整除, 则无需对齐)
             * 12     4        (loss due to the next object alignment)
             * -- (共占用16字节)
             * Instance size: 16 bytes
             * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
             */
            log.info(ClassLayout.parseInstance(object).toPrintable());
//            System.err.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }
}

class Person1 {

}
