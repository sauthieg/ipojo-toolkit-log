package org.ow2.ipojo.toolkit.log.internal.jul;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.ow2.ipojo.toolkit.log.spi.LoggingModule;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 24/11/10
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
@Component
@Provides
public class JavaUtilLoggingModule implements LoggingModule {

    public <T> T getLogger(Class<T> expected, String name) {

        // Quickly exists if the expected type is not in our boundaries
        if (!Logger.class.equals(expected)) {
            return null;
        }

        Logger logger = Logger.getLogger(name);
        return expected.cast(logger);
    }
}
