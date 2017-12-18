package com.ssm.util.datasource;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dqq.
 * Druid数据源获取方式
 */

public class DruidDataSource {
    private static DataSource ds = null;

    static {
        try {
            InputStream in = DruidDataSource.class.getClassLoader()
                    .getResourceAsStream("jdbc.properties");
            Properties props = new Properties();
            props.load(in);
            ds = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection openConnection() throws SQLException {
        return ds.getConnection();
    }

}