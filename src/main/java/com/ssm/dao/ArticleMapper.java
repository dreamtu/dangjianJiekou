package com.ssm.dao;

import com.ssm.model.Article;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List allTourist();//获取所有景点数据
    Article touristByTitle(String title);//根据景点名称获取景点数据
}