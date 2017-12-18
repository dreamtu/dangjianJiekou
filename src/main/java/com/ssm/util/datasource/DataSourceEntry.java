package com.ssm.util.datasource;

import org.aspectj.lang.JoinPoint;

/**
 * 数据源切换接口入口 有效提高系统的水平伸缩性
 *
 * @author dqq
 */
public interface DataSourceEntry {

    // 默认外网数据源 (default设置为null是因为当程序里没有找到相关联的源就会调用默认数据源)
    public final static String DEFAULT_SOURCE = null;


    /**
     * 还原数据源
     *
     * @param joinPoint
     */
     void restore(JoinPoint joinPoint);

    /**
     * 设置数据源
     *
     * @param dataSource
     */
     void set(String dataSource);

    /**
     * 获取数据源
     *
     * @return String
     */
     String get();

    /**
     * 清空数据源
     */
     void clear();
}