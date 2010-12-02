package org.ow2.ipojo.toolkit.log.internal.log4j;

import org.apache.log4j.spi.Configurator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.testng.annotations.*;

import java.net.URL;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 29/11/10
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public class Log4JLoggingModuleTestCase {

    @Test
    public void testModuleIsConfigured() throws Exception {
        // Mocks
        BundleContext mockBundleContext = mock(BundleContext.class);
        Bundle mockBundle = mock(Bundle.class);
        Configurator mockConfigurator = mock(Configurator.class);
        ServiceReference ref = mock(ServiceReference.class);

        // Behaviors
        when(mockBundleContext.getBundle()).thenReturn(mockBundle);
        when(mockBundle.getResource(anyString())).thenReturn(new URL("file://test.properties"));
        when(ref.getProperty("type")).thenReturn("properties");

        // Setup instance under test
        Log4JLoggingModule module = new Log4JLoggingModule(mockBundleContext);
        module.setResourceName("test.properties");
        module.bindConfigurator(mockConfigurator, ref);

        // Execute the method
        module.configure();
    }
}
