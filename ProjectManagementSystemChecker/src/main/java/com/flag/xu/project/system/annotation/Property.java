package com.flag.xu.project.system.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/11/11.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Property {
    String value() default "";
}
