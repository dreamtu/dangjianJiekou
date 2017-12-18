package com.ssm.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Created by dqq
 * SpringBeanUtil工具类 用于普通类或线程类主动获取spring bean 来调用service方法
 * web应用，可以直接通过classLoader拿到context实例，获取bean
 */
public class SpringBeanUtil {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz, String beanName) {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        if (context == null) {
            context = applicationContext;
        }
        return (T) context.getBean(beanName);
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanUtil.applicationContext = applicationContext;
    }
}
