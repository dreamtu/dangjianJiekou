package com.ssm.util.datasource;

import java.lang.annotation.*;


/**
 * 注解式数据源，用来进行数据源切换
 * 元注解是自定义注解的注解
 * User:dqq
 */

//@Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。
//ElementType.TYPE   用于描述类、接口(包括注解类型) 或enum声明，最常用。 ElementType.METHOD  作用于方法
@Target({ElementType.METHOD, ElementType.TYPE})
//@Retention定义了该Annotation被保留的时间长短 。RetentionPolicy.RUNTIME 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)
//@Documented是一个标记注解，没有成员,用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。
@Documented
//@Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。
@Inherited
public @interface DataSource {
    //默认主数据源数据库
    public static final String DEFAULT_DATASOURCE = "dataSource";
    //备份从数据源数据库
//    public static final String SLAVE_DATASOURCE = "dataSourceS";

    String name() default DataSource.DEFAULT_DATASOURCE;




}
