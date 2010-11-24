package org.ow2.ipojo.toolkit.log.spi;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 24/11/10
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public interface LoggingModule {

    /**
     *
     * @param expected expected Logger class Type
     * @param name Logger's name
     * @param <T> logger type
     * @return a configured logger instance, or null.
     */
    <T> T getLogger(Class<T> expected, String name);
}
