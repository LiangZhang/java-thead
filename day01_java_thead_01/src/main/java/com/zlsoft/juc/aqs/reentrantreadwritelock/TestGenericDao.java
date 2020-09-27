package com.zlsoft.juc.aqs.reentrantreadwritelock;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试：缓存优化
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020-09-27 11:40:19
*/
public class TestGenericDao {
    public static void main(String[] args) {
        //普通查询
        //genericSearch();
        //优化查询
        cacheSearch();
    }


    /**
     * 普通查询
     * @author: L.Z QQ.191288065@qq.com
     * @createTime 2020-09-27 11:23:42
     */
    private static void genericSearch() {
        GenericDao dao = new GenericDao();

        List<Emp> emps = dao.queryList(Emp.class, "select ename from emp");

        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql,empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }

    /**
     * 缓存优化：查询
     * @author: L.Z QQ.191288065@qq.com
     * @createTime 2020-09-27 11:22:54
    */
    private static void cacheSearch() {
        GenericDao dao = new GenericDaoCached();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 12, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }


}
