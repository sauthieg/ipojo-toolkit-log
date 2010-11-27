package org.ow2.ipojo.toolkit.log;

import org.apache.felix.ipojo.ComponentInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.ow2.chameleon.testing.helpers.IPOJOHelper;
import org.ow2.chameleon.testing.helpers.OSGiHelper;
import org.ow2.chameleon.testing.tinybundles.ipojo.IPOJOBuilder;
import org.ow2.ipojo.toolkit.log.component.LoggerComponent;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.newBundle;


/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 16/11/10
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
@RunWith( JUnit4TestRunner.class )
public class LoggingHandlerTestCase {

    @Configuration
    public static Option[] configure() {
        File metadata = new File("src/test/resources/metadata.xml");
        return options(
                mavenBundle("org.apache.felix", "org.apache.felix.ipojo").versionAsInProject(),
                mavenBundle("org.ow2.ipojo.toolkit", "log-handler").versionAsInProject(),
                mavenBundle("org.ow2.chameleon.testing", "osgi-helpers").versionAsInProject(),
                felix(),

                // Component bundle
                provision(newBundle()
                        .add(LoggerComponent.class)
                        .build(IPOJOBuilder.withiPOJO(metadata))
                )
        );
    }

    private OSGiHelper osgi;
    private IPOJOHelper ipojo;

    @Inject
    private BundleContext context;

    @Before
    public void setup() {
        osgi = new OSGiHelper(context);
        ipojo = new IPOJOHelper(context);

        // Prepare a JUL module instance
        ipojo.createComponentInstance("org.ow2.ipojo.toolkit.log.internal.jul.JavaUtilLoggingModule");
    }

    @After
    public void tearDown() {
        osgi.dispose();
        ipojo.dispose();
    }

    @Test
    public void testLoggerInjection() throws Exception {

        // Get the factory and try to crete an instance.
        ComponentInstance instance = ipojo.createComponentInstance(LoggerComponent.class.getName());

        // Instance should be valid ...
        assertThat(instance.getState(), is(ComponentInstance.VALID));
    }
}
