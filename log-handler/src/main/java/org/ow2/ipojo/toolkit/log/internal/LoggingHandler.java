package org.ow2.ipojo.toolkit.log.internal;

import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.PrimitiveHandler;
import org.apache.felix.ipojo.annotations.Handler;
import org.apache.felix.ipojo.metadata.Element;
import org.apache.felix.ipojo.parser.FieldMetadata;

import java.util.Dictionary;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 15/11/10
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
@Handler(name = "logging",
         namespace = "org.ow2.ipojo.toolkit.log")
public class LoggingHandler extends PrimitiveHandler {

    private static final String HANDLER_NAME = "logging";
    private static final String NAMESPACE = "org.ow2.ipojo.toolkit.log";

    @Override
    public void configure(Element metadata, Dictionary configuration) throws ConfigurationException {
        Element[] loggingElements = metadata.getElements(HANDLER_NAME, NAMESPACE);

        for (Element loggingElement : loggingElements) {

            // Traverse the logging element
            String fieldName = getFieldName(loggingElement);
            String name = getLoggerName(loggingElement);

            // By default, Logger's name is the supporting fully qualified class name
            if (name == null) {
                name = getInstanceManager().getClazz().getPackage().getName();
            }

            // Setup the injections point
            FieldMetadata field = getFieldMetadata(fieldName);
            getInstanceManager().register(field, new LoggerInjector(this, name));

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
     */
    private String getFieldName(Element loggingElement) throws ConfigurationException {
        return loggingElement.getAttribute("field");

    }

    @Override
    public void stop() {
        // Nothing to do
    }

    @Override
    public void start() {
        // Nothing to do
    }
}
