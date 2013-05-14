package com.playtech.cm.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 31.01.12
 * Time: 15:40
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigParam {
    String value();
    String splitBy() default "";
}
