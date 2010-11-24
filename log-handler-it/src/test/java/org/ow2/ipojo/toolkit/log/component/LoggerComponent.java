package org.ow2.ipojo.toolkit.log.component;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.ow2.ipojo.toolkit.log.Logging;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 16/11/10
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
@Component
public class LoggerComponent {

    @Logging
    private Logger logger;

    @Validate
    public void start() {
        // Try to use the logger, if the injection did not work we'll have NPE
        // Anyway, if the injection did not work, the component should never start
        logger.info("Logger has been injected");
    }
}
