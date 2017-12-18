package com.ssm.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dqq
 *         日志统一处理切面类  记录日志,异常信息等方便调试  不侵入代码  解耦合
 *         日志打印在控制台和保存在ssm.log文件中
 */
@Aspect
@Component
public class LogAdvice implements Ordered {
    //日志类
    private static Logger logger = Logger.getLogger(LogAdvice.class);

    @Pointcut("execution(* com.ssm..*.*(..))")
    //所有的方法调用
    private void logInvoke() {//增强处理

    }

    // 日志记录 环绕增强处理  功能最强的  调用方法前后都进行增强处理
    @Around("logInvoke()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        //动态获取类名
        String className = pjp.getTarget().getClass().getName();

        //动态获取方法名
        String methodName = pjp.getSignature().getName();

        //获取执行方法的参数
        Object[] args = pjp.getArgs();
        String argsvalue = "";

        //从参数列表中获取参数对象
        for (Object obj : args) {//查看参数值
            argsvalue += obj.toString() + "，";
        }
        argsvalue = argsvalue == "" ? "" : argsvalue.substring(0, argsvalue.length() - 1);//去掉最后的，号
        boolean flag=methodName.indexOf("getSSE")==-1 ?true :false;
        //log4j.properties配置debug调试级别打印
        if (logger.isDebugEnabled()&&flag) {
            logger.info("===SSM基础框架程序运行方法点:  " + className + "." + methodName + "(" + argsvalue + ")" + " 方法进入开始！");
        }
        //proceed()就是调用目标方法 可控制目标方法是否执行
        Object object = pjp.proceed();
        //log4j.properties配置debug调试级别打印
        if (logger.isDebugEnabled()&&flag) {
            logger.info("===SSM基础框架程序运行方法点:  " + className + "." + methodName + "(" + argsvalue + ")" + " 方法返回结束！");
        }
        return object;
    }

    //异常记录  异常增强处理
    @AfterThrowing(pointcut = "execution(* com.ssm..*.*(..))", throwing = "ex")
    public void interceptorException(Exception ex) throws Throwable {
        logger.info("=========  SSM基础框架程序异常错误记录  =========");
        logger.info("===SSM基础框架程序运行抛出的异常:    " + ex.getMessage());
        logger.info("===SSM基础框架程序异常发生的原因： " + ex.getCause());
        logger.info("===SSM基础框架程序运行异常详细信息：　　　" + ex.fillInStackTrace());
        logger.info("===SSM基础框架程序异常发生时间:    " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    //来处理相同接口实现类的优先级问题
    public int getOrder() {
        return 100;
    }
}
