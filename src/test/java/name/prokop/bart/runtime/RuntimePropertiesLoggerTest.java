/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.prokop.bart.runtime;

import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author prokob01
 */
public class RuntimePropertiesLoggerTest {

    public RuntimePropertiesLoggerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getJettyPort method, of class RuntimeProperties.
     */
    @Test
    public void testGetLoggerDirectory() {
        System.out.println("testGetLoggerDirectory(): " + RuntimeProperties.INSTANCE.getLoggerDirectory());
        assertEquals("../logs", RuntimeProperties.INSTANCE.getLoggerDirectory());
    }

    @Test
    public void testGetLoggerFile() {
        System.out.println("testGetLoggerFile(): " + RuntimeProperties.INSTANCE.getLoggerFile());
        assertEquals("runtime_current.log", RuntimeProperties.INSTANCE.getLoggerFile());
    }

    @Test
    public void testGetLoggerRollingPattern() {
        System.out.println("testGetLoggerRollingPattern(): " + RuntimeProperties.INSTANCE.getLoggerRollingPattern());
        assertEquals("runtime_%d{yyyy-MM-dd}.log", RuntimeProperties.INSTANCE.getLoggerRollingPattern());
    }
}
