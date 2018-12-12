package cn.itcast.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/13
 * Time:10:30
 */
public class DrurdUtils {

    /*第一步  加载配置文件
    * 第二步  根据配置文件获取到一个DateSource 数据源
    * 第三步  就可以根据数据源获取一个连接
    * */

    private static DataSource dataSource;

    static{
//        第一步  加载配置文件
        InputStream input = DrurdUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties pro = new Properties();
        try {
            pro.load(input);
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 获取连接*/
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /*返回一个数据源*/
    public static DataSource getDataSource(){
        return  dataSource;
    }

    public static void close(Connection conn, Statement stat, ResultSet resultSet) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(Connection conn, Statement stat) {
        close(conn,stat,null);
    }


}
