package com.ssm.service.interfaces;



import com.ssm.model.Article;

import java.util.List;
import java.util.Map;

/**
 * Created by dqq
 * 测试用的service接口
 */
public interface ITestService {
    Map<String, Object> getUserPaging();

    Article getTourist(String id);//根据景点id获取景点数据

    Article touristByTitle(String title);//根据景点名称获取景点数据

    List getAllTourist();//获取所有景点

}
