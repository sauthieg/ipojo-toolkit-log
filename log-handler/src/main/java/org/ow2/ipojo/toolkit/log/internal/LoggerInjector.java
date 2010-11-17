package org.ow2.ipojo.toolkit.log.internal;

import org.apache.felix.ipojo.FieldInterceptor;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 15/11/10
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class LoggerInjector implements FieldInterceptor {
    private LogPointHandler handler;
    private String name;

    public LoggerInjector(LogPointHandler handler, String name) {
        this.handler = handler;
        this.name = name;
    }

    public void onSet(Object pojo, String fieldName, Object value) {
        // do nothing
    }

    public Object onGet(Object pojo, String fieldName, Object value) {
        // TODO Should return a different instance given the expected type (JUL, JCL, ...)
        return Logger.getLogger(name);
    }
}
