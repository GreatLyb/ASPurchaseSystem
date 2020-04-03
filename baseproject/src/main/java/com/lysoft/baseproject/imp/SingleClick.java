package com.lysoft.baseproject.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VisitorIdentification
 * 类描述：
 * 类传参：
 *
 * @Author： Creat by Lyb on 2020/1/9 16:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
    /* 点击间隔时间 */
    long value() default 1000;
}
