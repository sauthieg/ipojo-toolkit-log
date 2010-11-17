package org.ow2.ipojo.toolkit.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.ow2.chameleon.testing.tinybundles.ipojo.IPOJOBuilder;
import org.ow2.ipojo.toolkit.log.api.ILogTestService;
import org.ow2.ipojo.toolkit.log.component.TestLoggerComponent;
import org.ow2.ipojo.toolkit.log.utils.OSGiSupportTestCase;

import java.io.File;

import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;




/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 16/11/10
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
@RunWith( JUnit4TestRunner.class )
public class LogPointHandlerTestCase extends OSGiSupportTestCase {

    @Configuration
    public static Option[] configure() {
        File metadata = new File("src/test/resources/metadata.xml");
        return options(
                mavenBundle("org.apache.felix", "org.apache.felix.ipojo").versionAsInProject(),
                mavenBundle("org.ow2.ipojo.toolkit", "log-handler").versionAsInProject(),
                felix().version("2.0.0"),
                // API bundle
                provision(newBundle()
                        .add(ILogTestService.class)
                        .set(Constants.EXPORT_PACKAGE,
                                ILogTestService.class.getPackage().getName())
                        .build(withBnd())),
                // Component bundle
                provision(newBundle()
                        .add(TestLoggerComponent.class)
                        .set(Constants.IMPORT_PACKAGE,
                                ILogTestService.class.getPackage().getName())
                        .build(IPOJOBuilder.withiPOJO(metadata))
                )
        );
    }


    /**
     * You will get a list of bundles installed by default
     * plus your testcase, wrapped into a bundle called pax-exam-probe
     */
    @Test
    public void testLoggerInjection() {
        ILogTestService service = getOsgiService(ILogTestService.class, null, 1000);
        assertThat(service.isLoggerInjected(), is(true));
    }
}
