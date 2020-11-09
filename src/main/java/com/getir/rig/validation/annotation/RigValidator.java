package com.getir.rig.validation.annotation;

import javax.ws.rs.core.Response;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RigValidator {

    /**
     * Validator that should be run before the rest controller method is invoked.
     */
    Class<?> validator();

    Response.Status status() default Response.Status.INTERNAL_SERVER_ERROR;
}
