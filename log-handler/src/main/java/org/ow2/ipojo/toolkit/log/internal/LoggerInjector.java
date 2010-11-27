package org.ow2.ipojo.toolkit.log.internal;

import org.apache.felix.ipojo.FieldInterceptor;
import org.osgi.framework.ServiceReference;
import org.ow2.ipojo.toolkit.log.spi.LoggingModule;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 15/11/10
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class LoggerInjector implements FieldInterceptor {
    private LoggingHandler handler;
    private String name;
    private Class<?> type;

    private LoggerReference reference = new InitialLoggerReference();

    public LoggerInjector(LoggingHandler handler, String name, Class<?> type) {
        this.handler = handler;
        this.name = name;
        this.type = type;
    }

    public void onSet(Object pojo, String fieldName, Object value) {
        // do nothing
    }

    public Object onGet(Object pojo, String fieldName, Object value) {

        if (!reference.isUsable()) {
            // Reference became invalid or it is the first invocation
            reference = handler.findLogger(type, name);
        }

        return reference.getLogger();
    }

    private class InitialLoggerReference extends LoggerReference {

        public InitialLoggerReference() {
            super(null, null);
        }

        @Override
        public boolean isUsable() {
            return false;
        }
    }

}
