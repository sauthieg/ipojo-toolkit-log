package org.ow2.ipojo.toolkit.log.internal.log4j;

import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.ow2.ipojo.toolkit.log.spi.LoggingModule;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 27/11/10
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public class Log4JLoggingModule implements LoggingModule {

    @ServiceProperty(mandatory = true, value = ".*")
    private String pattern;

    @Validate
    private void configure() {
        //LoggerFactory
    }

    public <T> T getLogger(Class<T> expected, String name) {
        // Quickly exists if the expected type is not in our boundaries
        if (!Logger.class.equals(expected)) {
            return null;
        }

        Logger logger = Logger.getLogger(name);
        return expected.cast(logger);
    }
}
