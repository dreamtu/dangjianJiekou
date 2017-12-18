
package com.ssm.util.datasource;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * AOP解析注解数据源，用来进行动态数据源切换  增强处理 不侵入代码  解耦合
 * User:dqq
 */
//通过@Aspect注解 将DataSourceAdvice类标示为一个切面 切面由切入点和通知(增强处理)构成
@Aspect
//@Component把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
@Component
//Spring就提供了Ordered这个接口，来处理相同接口实现类的优先级问题。
public class DataSourceAdvice implements Ordered {

    @Resource
    private DataSourceEntry dataSourceEntry = null;

    @Pointcut("execution(* com.ssm.service.interfaces.*.*(..))")
    //service里的方法调用
    private void serviceInvoke() {//增强处理

    }
    //环绕增强处理  功能最强的  调用方法前后都进行增强处理
    @Around("serviceInvoke()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {

        //ProceedingJoinPoint能获取目标方法参数，返回值等信息
        //动态在类上找@DataSource注解
        DataSource dataSource = pjp.getTarget().getClass().getAnnotation(DataSource.class);

        if (dataSource == null) {
            //在方法上找@DataSource注解
            //动态获取调用的方法名
            String methodName = pjp.getSignature().getName();
            //动态获取调用的方法里参数类型
            Class<?>[] parameters=((MethodSignature) pjp.getSignature()).getParameterTypes();
            dataSource = pjp.getTarget().getClass().getMethod(methodName,parameters).getAnnotation(DataSource.class);
        }
        if (dataSource != null) {
            //如果检测到需要切换，则进行处理
            dataSourceEntry.set(dataSource.name());
        }
        //proceed()就是调用目标方法 可控制目标方法是否执行
        Object object = pjp.proceed();
        //切面还原默认数据源 写的切面还原是为了保证每次调用完另外的数据源以后都会还原成默认数据源,防止有的人忘记设置回默认的,导致其他代码出问题
        if (dataSource != null) {
            //切换后清除
            dataSourceEntry.clear();
        }
        return object;
    }
    //来处理相同接口实现类的优先级问题
    public int getOrder() {
        return 100;
    }
}
