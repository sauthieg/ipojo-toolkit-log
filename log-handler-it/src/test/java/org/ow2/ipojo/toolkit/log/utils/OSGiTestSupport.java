package org.ow2.ipojo.toolkit.log.utils;

import org.ops4j.pax.exam.Inject;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 17/11/10
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public class OSGiTestSupport {

    @Inject
    protected BundleContext bundleContext;

    protected <T> T getOsgiService(Class<T> type, String filter, long timeout) {
            ServiceTracker tracker = null;
            try {
                String flt;
                if (filter != null) {
                    if (filter.startsWith("(")) {
                        flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")" + filter + ")";
                    } else {
                        flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")(" + filter + "))";
                    }
                } else {
                    flt = "(" + Constants.OBJECTCLASS + "=" + type.getName() + ")";
                }
                Filter osgiFilter = FrameworkUtil.createFilter(flt);
                tracker = new ServiceTracker(bundleContext, osgiFilter, null);
                tracker.open();
                // Note that the tracker is not closed to keep the reference
                // This is buggy, has the service reference may change i think
                Object svc = type.cast(tracker.waitForService(timeout));
                if (svc == null) {
                    throw new RuntimeException("Gave up waiting for service " + flt);
                }
                return type.cast(svc);
            } catch (InvalidSyntaxException e) {
                throw new IllegalArgumentException("Invalid filter", e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

}
