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
public class RuntimePropertiesSpringTest {

    public RuntimePropertiesSpringTest() {
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

    @Test
    public void testIsJettyEnabled() {
        System.out.println("getSpringContext(): " + RuntimeProperties.INSTANCE.getSpringContext());
        assertEquals("classpath*:/META-INF/runtime-context.xml", RuntimeProperties.INSTANCE.getSpringContext());
    }
}
