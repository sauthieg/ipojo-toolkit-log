package org.ow2.ipojo.toolkit.log.component;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.ow2.ipojo.toolkit.log.Logging;
import org.ow2.ipojo.toolkit.log.api.ILogTestService;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 16/11/10
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
@Component
@Instantiate
@Provides
public class LoggerComponent implements ILogTestService {

    @Logging
    private Logger logger;

    public boolean isLoggerInjected() {
        return logger != null;
    }

    @Validate
    public void start() {
        logger.info("BLABLABLABLABLA");
    }
}
