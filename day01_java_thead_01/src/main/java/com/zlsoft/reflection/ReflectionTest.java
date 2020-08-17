package com.zlsoft.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.doubleSingleton
 * @ClassName: ReflectionTest.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 通过反射创建类对象
 * @createTime 2020年08月14日 12:02:00
 */
public class ReflectionTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        test5();
    }

    /**
     *  通过空构造函数反射创建类对象：
     * 通过Class.newInstance()方式创建类对象的对象实例，本质是执行了类对象的默认的空参的构造函数，如果类对象含有非空的构造函数，并且没有显式的声明空参的构造函数，
     * 通过Class.newInstance()方式来创建类对象的实例时，会抛出java.lang.NoSuchMethodException异常。因此，开发者在设计通过反射创建类对象的对象实例时，
     * 一定要判断区分空参的构造方法
     */
    private static void test1() throws IllegalAccessException, InstantiationException  {
        //如果去掉Persond的默认构造函数
        //Exception in thread "main" java.lang.InstantiationException: com.zlsoft.doubleSingleton.Person
        Person person = Person.class.newInstance();
        person.setAge(18);
        person.setName("likly");
        System.out.println(person);
    }

    /**
     * 通过反射获取构造方法实例，然后再创建对象实例
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static void test2() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class p = Person.class;
        Constructor constructor = p.getConstructor(String.class,int.class);
        Person person = (Person) constructor.newInstance("Likly",23);
        System.out.println(person);
    }

    /**
     * 反射取得所有构造函数
     */
    private static void test3() {
        Class p = Person.class;
        for(Constructor constructor : p.getConstructors()){
            System.out.println(constructor);
        }
    }

    /**
     * 反射取得成员变量
     */
    private static void test4() {
        Class p = Person.class;
        Field[] declaredFields = p.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
    }

    /**
     * 利用反射暴力访问私有构造函数
     */
    private static void test5() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //get Constructor
//        Class clazz = Class.forName("T");
//        Constructor cons = clazz.getDeclaredConstructor(null);

        Class p = Person.class;
//        Constructor cons = p.getDeclaredConstructor();
//        Constructor constructor = p.getConstructor(String.class,int.class,int.class);
//        constructor.setAccessible(true);
//        Person person = (Person) constructor.newInstance("张三", 15, 6);
//        System.out.println(person);

        Constructor constructor = p.getDeclaredConstructor(String.class,int.class,int.class);
        constructor.setAccessible(true);
        Person person = (Person) constructor.newInstance("张三",15,20);
        System.out.println(person);
    }

}
    
    
    