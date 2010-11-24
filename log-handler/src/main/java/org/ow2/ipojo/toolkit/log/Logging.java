package org.ow2.ipojo.toolkit.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 15/11/10
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {

    /**
     * Name of the logger (optional).
     * Defaults to the supporting component's fully qualified class name.
     */
    String value() default "";
}
