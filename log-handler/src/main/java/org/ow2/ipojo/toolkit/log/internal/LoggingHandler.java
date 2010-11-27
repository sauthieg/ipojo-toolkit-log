package org.ow2.ipojo.toolkit.log.internal;

import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.PrimitiveHandler;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Handler;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.metadata.Element;
import org.apache.felix.ipojo.parser.FieldMetadata;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.ow2.ipojo.toolkit.log.spi.LoggingModule;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This handler is responsible to inject Loggers into the component.
 * Using this handler free the component developer from the usual static factory pattern.
 *
 * @author Guillaume Sauthier
 */
@Handler(name = "logging",
         namespace = "org.ow2.ipojo.toolkit.log")
public class LoggingHandler extends PrimitiveHandler {

    /**
     * Handler's name.
     */
    private static final String HANDLER_NAME = "logging";

    /**
     * Handler's namespace.
     */
    private static final String NAMESPACE = "org.ow2.ipojo.toolkit.log";

    /**
     * All registered modules.
     */
    private Map<ServiceReference, LoggingModule> modules = new HashMap<ServiceReference, LoggingModule>();

    @Override
    public void configure(final Element metadata,
                          final Dictionary configuration) throws ConfigurationException {

        // Get the logging handler declaration(s)
        Element[] loggingElements = metadata.getElements(HANDLER_NAME, NAMESPACE);

        // Iterate over the handler declaration(s)
        for (Element loggingElement : loggingElements) {

            // Traverse the logging element
            String fieldName = getFieldName(loggingElement);
            String name = getLoggerName(loggingElement);

            // By default, Logger's name is the supporting fully qualified class name
            if (name == null) {
                name = getInstanceManager().getClazz().getName();
            }

            // Setup the injections point
            FieldMetadata field = getFieldMetadata(fieldName);
            Class<?> type = null;
            try {
                type = getFactory().loadClass(field.getFieldType());
            } catch (ClassNotFoundException e) {
                throw new ConfigurationException("Cannot load field Class from " + field);
            }
            getInstanceManager().register(field, new LoggerInjector(this, name, type));

        }

    }

    /**
     * Find the matching field Metadata from the field name.
     * @param fieldName name of the field to find
     * @return FieldMetadata
     */
    private FieldMetadata getFieldMetadata(String fieldName) {
        return getPojoMetadata().getField(fieldName);
    }

    /**
     * Get the user specified (if any) logger's name.
     * @param loggingElement the supporting Element structure
     * @return value of the 'value' attribute (or null)
     */
    private String getLoggerName(Element loggingElement) {
        return loggingElement.getAttribute("value");
    }

    /**
     * Get the name of the field that will be injected.
     * @param loggingElement the supporting Element structure
     * @return value of the 'field' attribute
     * @throws ConfigurationException when field is unknown
     */
    private String getFieldName(Element loggingElement) throws ConfigurationException {
        return loggingElement.getAttribute("field");

    }

    @Bind(aggregate = true, optional = true)
    public synchronized void bindLoggingModule(LoggingModule module, ServiceReference reference) {
        String pattern = (String) reference.getProperty("pattern");
        Pattern compiled = Pattern.compile(pattern);
        Matcher matcher = compiled.matcher(getBundleSymbolicName());
        if (matcher.matches()) {
            // The bundle of this instance can be configured with the module
            modules.put(reference, module);
        }

    }

    private String getBundleSymbolicName() {
        return getInstanceManager().getContext().getBundle().getSymbolicName();
    }

    @Unbind
    public synchronized void unbindLoggingModule(ServiceReference reference) {
        modules.remove(reference);
    }

    @Override
    public void stop() {
        // Nothing to do
    }

    @Override
    public void start() {
        // Nothing to do
    }

    public LoggerReference findLogger(Class<?> type, String name) {
        for (Map.Entry<ServiceReference, LoggingModule> entry : modules.entrySet()) {
            Object logger = entry.getValue().getLogger(type, name);
            if (logger != null) {
                return new LoggerReference(entry.getKey(), logger);
            }
        }

        this.setValidity(false);
        // TODO What happen when the handler change its state within the user's Thread (aka while a normal execution ?)
        // Does the processing stops immediately ?

        // FIXME, Probably some NPE after that
        return null;
    }
}
