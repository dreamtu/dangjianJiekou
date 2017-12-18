package com.ssm.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/20 0020.
 * 公共工具类
 */
public class CommonUtil {

    /**
     * 去除分页map里面无效数据
     */
    public static Map<String, Object> removePaingMap(Map map) {
        Map<String, Object> newMap = new HashMap();
        map.remove("tableName");
        map.remove("fields");
        map.remove("wherecase");
        map.remove("orderField");
        map.remove("orderFlag");
        newMap = map;
        return newMap;
    }

    /**
     * 组装restful API返回的JSON数据
     *
     * @param status 状态码  200 操作成功, 101 执行成功,但无数据 102 授权失败,103 执行异常,104 参数为空,105 参数错误,106 执行成功,但未返回正确结果,107 数据已存在，判断唯一性数据
     * @param info   状态描述
     * @param data   返回数据
     * @return 返回json数据
     */
    public static Map<String, Object> resultJSON(String status, String info, Object data) {
        Map<String, Object> newMap = new HashMap();
        newMap.put("status", status);
        newMap.put("info", info);
        newMap.put("data", data);
        return newMap;
    }
}
