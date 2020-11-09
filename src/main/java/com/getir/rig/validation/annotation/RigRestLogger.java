package com.getir.rig.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RigRestLogger {

    String name() default "";
    boolean logRequest() default true;
    boolean logResponse() default true;
    int maxSize() default 10000;
    boolean logMode() default false;
}
