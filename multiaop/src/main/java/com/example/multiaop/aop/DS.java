package com.example.multiaop.aop;
import java.lang.annotation.*;


/**
 * 自定义多数据源切换注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DS {
    String value() default "test";
}
