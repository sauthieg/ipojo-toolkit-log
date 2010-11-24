package org.ow2.ipojo.toolkit.log.example;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.ow2.ipojo.toolkit.log.Logging;

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

    @Logging
    public Logger logger1;

    @Logging("another.logger.name")
    public Logger logger2;

    @Validate
    public void start() {
        logger1.info(LoggableComponent.class.getSimpleName() + "[" + logger1.getName() + "] started.");
        logger2.info(LoggableComponent.class.getSimpleName() + "[" + logger2.getName() + "] started.");
    }

    @Invalidate
    public void stop() {
        logger1.info(LoggableComponent.class.getSimpleName() + "[" + logger1.getName() + "] stopped.");
        logger2.info(LoggableComponent.class.getSimpleName() + "[" + logger2.getName() + "] stopped.");
    }
}
