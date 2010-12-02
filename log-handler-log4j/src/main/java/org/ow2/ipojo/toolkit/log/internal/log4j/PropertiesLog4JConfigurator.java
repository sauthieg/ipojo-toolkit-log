package org.ow2.ipojo.toolkit.log.internal.log4j;

import org.apache.felix.ipojo.annotations.*;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 29/11/10
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
@Component
@Provides
@Instantiate
public class PropertiesLog4JConfigurator implements Configurator {

    /**
     * File extension marker.
     */
    @ServiceProperty(mandatory = true, value = "properties")
    private String type;

    private Configurator delegate;

    @Validate
    public void start() {
        delegate = new PropertyConfigurator();
    }

    public void doConfigure(URL url, LoggerRepository repository) {
        delegate.doConfigure(url, repository);
    }
}
