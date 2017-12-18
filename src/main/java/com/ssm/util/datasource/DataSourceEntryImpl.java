package com.ssm.util.datasource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * 数据源动态切换实现类 多数据有效提高系统的水平伸缩性
 *
 * @author dqq
 */
//注入spring容器管理
@Component
public class DataSourceEntryImpl implements DataSourceEntry {
    //ThreadLocal为解决多线程程序的并发问题提供了一种新的思路
    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    /**
     * 还原数据源
     *
     * @param joinPoint 连接点
     */
    @Override
    public void restore(JoinPoint joinPoint) {
        local.set(DEFAULT_SOURCE);
    }

    /**
     * 设置数据源名称
     *
     * @param dataSource
     */
    @Override
    public void set(String dataSource) {
        local.set(dataSource);
    }

    /**
     * 获取数据源
     *
     * @return String
     */
    @Override
    public String get() {
        return local.get();
    }

    /**
     * 清空数据源
     */
    @Override
    public void clear() {
        local.remove();
    }
}