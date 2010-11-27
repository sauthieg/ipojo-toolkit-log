package org.ow2.ipojo.toolkit.log.internal;

import org.osgi.framework.ServiceReference;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 25 nov. 2010
 * Time: 20:03:32
 * To change this template use File | Settings | File Templates.
 */
public class LoggerReference {

    private ServiceReference reference;
    private Object logger;

    public LoggerReference(ServiceReference reference, Object logger) {
        this.reference = reference;
        this.logger = logger;
    }

    public ServiceReference getReference() {
        return reference;
    }

    public Object getLogger() {
        return logger;
    }

    public boolean isUsable() {
        return reference.getBundle() != null;
    }
}
