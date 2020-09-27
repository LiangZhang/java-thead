package com.zlsoft.juc.aqs.reentrantreadwritelock;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.juc.aqs.reentrantreadwritelock
 * @ClassName: GenericDaoCached.java
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020年09月27日 11:25:00
 * @Description 缓存优化查询
 */
public class GenericDaoCached extends GenericDao {
    private GenericDao dao = new GenericDao();
    /**
     * sql语句整体作为key:SqlPair
     *  -问题1：HashMap,多线程是不安全的
     */
    private Map<SqlPair, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    @Override
    public <T> List<T> queryList(Class<T> beanClass, String sql, Object... args) {
        return dao.queryList(beanClass, sql, args);
    }

    //#region v1 -查询、更新

    @Override
    public <T> T queryOne(Class<T> beanClass, String sql, Object... args) {
        // 1.先从缓存中找，找到直接返回
        SqlPair sqlPair = new SqlPair(sql, args);

        /**
         * -问题1：HashMap,多线程是不安全的，或者产生死链接的问题
         * -问题2：多线程首次执行queryOne都会执行查询数据库的问题
         */
        T value = (T) map.get(sqlPair);
        if (value != null) {
            return value;
        }

        // 2.缓存中没有，查询数据库
        value = dao.queryOne(beanClass, sql, args);
        map.put(sqlPair, value);
        return value;
    }


    /**
     * 缓存更新策略：更新数据的时候，是先更新缓存，还是先更新数据库？（数据不一致）
     *  方案l: （先清除缓存，在存数据）线程A先清空缓存，在更新数据库,由于写过程比较慢，那么其他线程有可能已经进行了多次查询，那么缓存值有可能是旧值。
     *  方案2: （先存数据，在更新缓存）线程A更新数据库,其他线程有可能查询到旧值，造成数据不一致，但是后续可以纠正。
     * @param sql
     * @param args
     * @return
     */
    @Override
    public int update(String sql, Object... args) {
        //清空缓存
        map.clear();
        //更新数据库
        return dao.update(sql,args);
    }
    //#endregion

    //#region v3 -查询、更新

    private <T> T cacheQueryOne(Class<T> beanClass, String sql, Object[] args) {
        // 1.先从缓存中找，找到直接返回
        SqlPair key = new SqlPair(sql, args);
        rw.readLock().lock();
        try {
            T value = (T) map.get(key);
            if(value != null) {
                return value;
            }
        } finally {
            rw.readLock().unlock();
        }
        rw.writeLock().lock();
        try {
            // 多个线程
            T value = (T) map.get(key);
            if(value == null) {

                value = dao.queryOne(beanClass, sql, args);
                map.put(key, value);
            }
            return value;
        } finally {
            rw.writeLock().unlock();
        }
    }
    /**
     * 更新缓存和数据库
     * @param sql
     * @param args
     * @return
     */
    private int cacheUpdate(String sql, Object[] args) {
        rw.writeLock().lock();
        try {
            // 先更新库
            int update = dao.update(sql, args);
            // 清空缓存
            map.clear();
            return update;
        } finally {
            rw.writeLock().unlock();
        }
    }

    //#endregion

    //#region 缓存Key
    /**
     * cache参数 key：
     *  -保证不可变，
     *  -重写equals,hashCode
     * @author: L.Z QQ.191288065@qq.com
     * @createTime 2020-09-27 16:09:22
     */
    class SqlPair{
        /**
         * sql语句
         */
        private String sql;
        /**
         * sql语句的参数
         */
        private Object[] args;

        public SqlPair(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SqlPair sqlPair = (SqlPair) o;
            return Objects.equals(sql, sqlPair.sql) &&
                    Arrays.equals(args, sqlPair.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(sql);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
    //#endregion

}
    
    
    