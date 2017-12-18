package com.ssm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常用字符串工具类
 */
public class StringHelper {

    /**
     * 获取订单流水号  时间（到毫秒）+三位随机数
     *
     * @return
     */
    public static String getOrderNum() {
        try {
            Thread.sleep(1L);//处理并发问题 延迟1毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsssss");
        String num = Integer.toString((int) ((999 - 100) * Math.random() + 100));//取得一个100-999的3位随机数字字符串
        String orderNum = format.format(date) + num;
        return orderNum;
    }


}
