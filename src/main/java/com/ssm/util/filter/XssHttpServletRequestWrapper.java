package com.ssm.util.filter;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by dqq
 * 防止sql注入，xss跨站攻击的功能的封装类
 * 需要继承HttpServletRequestWrapper并且覆盖你希望改变的方法
 * escapeSql 提供sql转移功能，防止sql注入攻击，例如典型的万能密码攻击' ' or 1=1 ' '
 * escapeHtml escapeJavaScript等方法 把特殊字符转义之后的字符串 防止xss注入攻击
 * 解决过程主要在用户输入和显示输出两步：在输入时对特殊字符如<>" ' & 转义，
 * 在输出时用jstl的fn:excapeXml("fff")方法 fn:escapeXml()对有可能出现xss漏洞的地方做一下转义输出
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * XssHttpServletRequestWrapper构造方法 参数HttpServletRequest
     *
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * http请求头文件方法重写相应的几个有可能带xss攻击的方法进行转义
     */
    @Override
    public String getHeader(String name) {
        return StringEscapeUtils.escapeHtml4(super.getHeader(name));
    }

    /**
     * http请求URL查询字符串（?后的参数）方法重写相应的几个有可能带xss攻击的方法进行转义
     */
    @Override
    public String getQueryString() {
        return StringEscapeUtils.escapeHtml4(super.getQueryString());
    }

    /**
     * http请求获取参数方法重写相应的几个有可能带xss攻击的方法进行转义
     */
    @Override
    public String getParameter(String name) {
        return StringEscapeUtils.escapeHtml4(super.getParameter(name));
    }

    /**
     * http请求获取所有参数的值的方法重写相应的几个有可能带xss攻击的方法进行转义
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

}