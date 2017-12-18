package com.ssm.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;

/**
 * 动态获取数据源(依赖SPRING框架的AbstractRoutingDataSource)
 *
 * @author dqq
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    //注入数据源切换接口
    @Resource
    private DataSourceEntry dataSourceEntry;

    //获取数据源   在其中封装数据源的选择逻辑
    @Override
    protected Object determineCurrentLookupKey() {
        return this.dataSourceEntry.get();
    }



}