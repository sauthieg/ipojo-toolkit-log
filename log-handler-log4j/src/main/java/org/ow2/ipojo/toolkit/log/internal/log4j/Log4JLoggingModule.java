package org.ow2.ipojo.toolkit.log.internal.log4j;

import org.apache.felix.ipojo.annotations.*;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.ow2.ipojo.toolkit.log.spi.LoggingModule;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Configurator> configurators = new HashMap<String, Configurator>();

    public Log4JLoggingModule(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Validate
    public void configure() {
        // Init the LoggerRepository instance
        RootLogger root = new RootLogger(Level.DEBUG);
        repository = new Hierarchy(root);

        // Load the resource as an URL
        URL resource = bundleContext.getBundle().getResource(resourceName);
        // and find the appropriate Configurator
        Configurator configurator = configurators.get(getExtension());

        if (configurator == null) {
            throw new IllegalStateException("No Configurator support for resource " + resource);
        }

        // Configure the repository
        configurator.doConfigure(resource, repository);
    }

    private String getExtension() {
        int index = resourceName.lastIndexOf(".");
        if (index != -1) {
            return resourceName.substring(index + 1);
        }

        return resourceName;
    }

    public <T> T getLogger(Class<T> expected, String name) {
        // Quickly exists if the expected type is not in our boundaries
        if (!Logger.class.equals(expected)) {
            return null;
        }

        Logger logger = repository.getLogger(name);
        return expected.cast(logger);
    }

    @Bind(aggregate = true)
    public void bindConfigurator(Configurator configurator, ServiceReference reference) {
        String type = (String) reference.getProperty("type");
        configurators.put(type, configurator);
    }

    @Unbind
    public void unbindConfigurator(ServiceReference reference) {
        String type = (String) reference.getProperty("type");
        configurators.remove(type);
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }


}
