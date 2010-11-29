package org.ow2.ipojo.toolkit.log.internal.log4j;

import org.apache.felix.ipojo.annotations.*;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.osgi.framework.BundleContext;
import org.ow2.ipojo.toolkit.log.spi.LoggingModule;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 27/11/10
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
@Component
@Provides
public class Log4JLoggingModule implements LoggingModule {

    @ServiceProperty(mandatory = true, value = ".*")
    private String pattern;

    @Property(name = "resource", mandatory = true)
    private String resourceName;

    private BundleContext bundleContext;
    private LoggerRepository repository;

    public Log4JLoggingModule(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Validate
    private void configure() {
        RootLogger root = new RootLogger(Level.DEBUG);
        repository = new Hierarchy(root);

        URL resource = bundleContext.getBundle().getResource(resourceName);

        if (resourceName.endsWith(".xml")) {
            new DOMConfigurator().doConfigure(resource, repository);
        } else {
            new PropertyConfigurator().doConfigure(resource, repository);
        }
    }

    public <T> T getLogger(Class<T> expected, String name) {
        // Quickly exists if the expected type is not in our boundaries
        if (!Logger.class.equals(expected)) {
            return null;
        }

        Logger logger = repository.getLogger(name);
        return expected.cast(logger);
    }
}
