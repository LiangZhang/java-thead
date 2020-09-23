package com.zlsoft.cas;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.cas
 * @ClassName: AtomicFieldTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 字段更新器：
 *  - 利用字段更新器，可以针对对象的某个域（Field：属性或者成员方法）进行原子操作，只能配合 volatile 修饰的字段使用，否则会出现异常
 * @createTime 2020年09月02日 10:18:00
 */
public class AtomicFieldTest {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Student1 stu = new Student1();

        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Student1.class, String.class, "name");



        System.out.println(updater.compareAndSet(stu, null, "张三"));

//        Object o = updater.get(Student1.class);
//        System.out.println(o);


        System.out.println(updater.compareAndSet(stu, "张三", "张三1"));

        System.out.println(stu);
    }
}

class Student1 {
   volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
    
    
    