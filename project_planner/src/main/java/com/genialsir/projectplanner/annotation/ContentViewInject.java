package com.genialsir.projectplanner.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author genialsir@163.com (GenialSir) on 2019/8/30
 */
//运行时注解
@Retention(RetentionPolicy.RUNTIME)
//类、接口类型注解
@Target(ElementType.TYPE)
public @interface ContentViewInject {
    int contentViewID() default -1;
}
