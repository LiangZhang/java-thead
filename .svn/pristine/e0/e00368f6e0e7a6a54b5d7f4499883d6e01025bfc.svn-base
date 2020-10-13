package com.zlsoft.juc.aqs.reentrantreadwritelock;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

/**
 *     今天出现编码出现了No suitable driver found for jdbc，又是找遍了网上的资料，基本上都说是三个问题：
 *     一是：连接URL格式出现了问题(Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/XX","root","XXXX")
 *     二是：驱动字符串出错(com.mysql.jdbc.Driver)
 *     三是Classpath中没有加入合适的mysql_jdbc驱动
 *     经过我的仔细检查，这三种错误我都没有犯，为什么呢？
 *     尝试着将mysql-connector-java-3.1.14-bin.jar的jar包加入C:\Program Files\Java\jre1.6.0_02\lib\ext文件夹下，问题解决了！！
 *     原来是不仅仅要求将驱动加入classpath中，而且需要将该jar包加入到java运行环境的外部jar包中，唉，下次这种低级错误还是少犯为妙。
 */

/**
 *  my-sql:配置
 *     > username: mysql2008
 *     > password: 77993453@@zh
 *     > #   url: jdbc:mysql://192.168.0.135:3307/test
 *     > #   java.sql.SQLException: The server time zone value '?й???????' is unrecognized or represents more tha
 *     > #   mysql连接高版本
 *     > url: jdbc:mysql://127.0.0.1:3307/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
 *     > driver-class-name: com.mysql.cj.jdbc.Driver
 *     > type: com.alibaba.druid.pool.DruidDataSource
 *     -加入jar：C:\Program Files\Java\jre1.8.0_221\lib\ext\
 * @author: L.Z QQ.191288065@qq.com
 * @createTime 2020-09-27 11:17:11
*/
public class GenericDao {
    static String URL = "jdbc:mysql://192.168.0.135:3307/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String USERNAME = "mysql2008";
    static String PASSWORD = "77993453@@zh";

    /**
     * 查询集合
     * @author: L.Z QQ.191288065@qq.com
     * @createTime 2020-09-27 11:21:22
    */
    public <T> List<T> queryList(Class<T> beanClass, String sql, Object... args) {
        System.out.println("sql: [" + sql + "] params:" + Arrays.toString(args));
        BeanRowMapper<T> mapper = new BeanRowMapper<>(beanClass);
        return queryList(sql, mapper, args);
    }

    /**
     * 查询单个
     * @author: L.Z QQ.191288065@qq.com
     * @createTime 2020-09-27 11:21:36
    */
    public <T> T queryOne(Class<T> beanClass, String sql, Object... args) {
        System.out.println("sql: [" + sql + "] params:" + Arrays.toString(args));
        BeanRowMapper<T> mapper = new BeanRowMapper<>(beanClass);
        return queryOne(sql, mapper, args);
    }
    private <T> List<T> queryList(String sql, RowMapper<T> mapper, Object... args) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        psmt.setObject(i + 1, args[i]);
                    }
                }
                List<T> list = new ArrayList<>();
                try (ResultSet rs = psmt.executeQuery()) {
                    while (rs.next()) {
                        T obj = mapper.map(rs);
                        list.add(obj);
                    }
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T queryOne(String sql, RowMapper<T> mapper, Object... args) {
        List<T> list = queryList(sql, mapper, args);
        return list.size() == 0 ? null : list.get(0);
    }

    public int update(String sql, Object... args) {
        System.out.println("sql: [" + sql + "] params:" + Arrays.toString(args));
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        psmt.setObject(i + 1, args[i]);
                    }
                }
                return psmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    interface RowMapper<T> {
        T map(ResultSet rs);
    }
    static class BeanRowMapper<T> implements RowMapper<T> {

        private Class<T> beanClass;
        private Map<String, PropertyDescriptor> propertyMap = new HashMap<>();

        public BeanRowMapper(Class<T> beanClass) {
            this.beanClass = beanClass;
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyMap.put(pd.getName().toLowerCase(), pd);
                }
            } catch (IntrospectionException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public T map(ResultSet rs) {
            try {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                T t = beanClass.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    PropertyDescriptor pd = propertyMap.get(columnLabel.toLowerCase());
                    if (pd != null) {
                        pd.getWriteMethod().invoke(t, rs.getObject(i));
                    }
                }
                return t;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
