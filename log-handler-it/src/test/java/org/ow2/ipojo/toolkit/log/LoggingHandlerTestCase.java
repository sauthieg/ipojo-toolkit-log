package org.ow2.ipojo.toolkit.log;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ow2.chameleon.testing.tinybundles.ipojo.IPOJOBuilder;
import org.ow2.ipojo.toolkit.log.component.LoggerComponent;
import org.ow2.ipojo.toolkit.log.utils.OSGiTestSupport;

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
public class LoggingHandlerTestCase extends OSGiTestSupport {

    @Configuration
    public static Option[] configure() {
        File metadata = new File("src/test/resources/metadata.xml");
        return options(
                mavenBundle("org.apache.felix", "org.apache.felix.ipojo").versionAsInProject(),
                mavenBundle("org.ow2.ipojo.toolkit", "log-handler").versionAsInProject(),
                felix(),

                // Component bundle
                provision(newBundle()
                        .add(LoggerComponent.class)
                        .build(IPOJOBuilder.withiPOJO(metadata))
                )
        );
    }


    /**
     * You will get a list of bundles installed by default
     * plus your testcase, wrapped into a bundle called pax-exam-probe
     */
    @Test
    public void testLoggerInjection() throws Exception {
        Factory factory = getOsgiService(Factory.class, "(factory.name=" + LoggerComponent.class.getName() + ")", 200);
        ComponentInstance instance = factory.createComponentInstance(null);

        // Instance should be valid ...
        assertThat(instance.getState(), is(ComponentInstance.VALID));
    }
}
