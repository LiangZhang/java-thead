package com.zlsoft;

import org.junit.jupiter.api.Test;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft
 * @ClassName: TestBase.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年06月28日 12:04:00
 */

/**
 * int和Integer的区别
 * 1、Integer是int的包装类，int则是java的一种基本数据类型
 * 2、Integer变量必须实例化后才能使用，而int变量不需要
 * 3、Integer实际是对象的引用，当new一个Integer时，实际上是生成一个指针指向此对象；而int则是直接存储数据值
 * 4、Integer的默认值是null，int的默认值是0
 */
public class TestBase {
    @Test
    public void testEquals() {
        int int1 = 12;
        int int2 = 12;
        int int3 = 129;

        Integer integer1 = new Integer(12);
        Integer integer2 = new Integer(12);
        Integer integer3 = new Integer(127);
        Integer integer4 = new Integer(129);

        Integer a1 = 127; //或者写成Integer a1 = Integer.valueOf(127);
        Integer a2 = 127;//或者写成Integer a2 = Integer.valueOf(127);

        Integer a = 128;
        Integer b = 128;

        System.out.println("int1 == int2 -> " + (int1 == int2));
        //Integer会自动拆箱为int，所以为true
        System.out.println("int1 == integer1 -> " + (int1 == integer1));
        System.out.println("int3 == integer4 -> " + (int3 == integer4));
        System.out.println("integer1 == integer2 -> " + (integer1 == integer2));
        System.out.println("integer3 == a1 -> " + (integer3 == a1));
        //对于-128到127之间的数，会进行缓存，Integer a1 = 127时，会将127进行缓存，下次再写Integer i2 = 127时，就会直接从缓存中取，就不会new了。所以22行的结果为true,而25行为false。
        //IntegerCache
        System.out.println("a1 == a2 -> " + (a1 == a2));
        //大于127，每次回 new Integer("128"),那个两个对象肯定不相等
        System.out.println("a == b -> " + (a == b));
    }
}