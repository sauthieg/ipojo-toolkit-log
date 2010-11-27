package org.ow2.ipojo.toolkit.log.internal.jul;

import org.testng.annotations.*;

import java.util.logging.Logger;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 27 nov. 2010
 * Time: 22:02:07
 * To change this template use File | Settings | File Templates.
 */
public class JavaUtilLoggingModuleTestCase {

    @Test
    public void testLoggerIsFound() throws Exception {
        JavaUtilLoggingModule module = new JavaUtilLoggingModule();
        Logger logger = module.getLogger(Logger.class, "name of the logger");
        assertNotNull(logger);
        assertEquals("name of the logger", logger.getName());

    }

    @Test
    public void testNoLoggerIsFound() throws Exception {
        JavaUtilLoggingModule module = new JavaUtilLoggingModule();
        Object logger = module.getLogger(Object.class, "name of the logger");
        assertNull(logger);
    }
}
