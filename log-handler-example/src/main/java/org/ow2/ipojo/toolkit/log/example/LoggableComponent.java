package org.ow2.ipojo.toolkit.log.example;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.ow2.ipojo.toolkit.log.LogPoint;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 22/11/10
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
@Component
@Instantiate
public class LoggableComponent {

    @LogPoint
    public Logger logger;

    @Validate
    public void start() {
        logger.info(LoggableComponent.class.getSimpleName() + " started.");
    }

    @Invalidate
    public void stop() {
        logger.info(LoggableComponent.class.getSimpleName() + " stopped.");
    }
}
