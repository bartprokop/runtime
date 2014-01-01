package name.prokop.bart.runtime;

import java.util.Iterator;
import java.util.Properties;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Bart Prokop
 */
public class RuntimePropertiesTest {

    public RuntimePropertiesTest() {
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
     * Test of values method, of class RuntimeProperties.
     */
    @Test
    public void testPrintValues() {
        System.out.println("testPrintValues()");
        Properties properties = RuntimeProperties.INSTANCE.getProperties();
        Iterator<Object> iterator = properties.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            System.out.println(key + " = " + properties.getProperty(key));
        }
    }

    @Test
    public void testGetProperties() {
        System.out.println("testGetProperties()");
        assertNotNull(RuntimeProperties.INSTANCE.getProperties());
        assertNotSame(0, RuntimeProperties.INSTANCE.getProperties().size());
    }

    @Test
    public void testIsJettyEnabled() {
        System.out.println("testIsJettyEnabled(): " + RuntimeProperties.INSTANCE.isJettyEnabled());
        assertTrue(RuntimeProperties.INSTANCE.isJettyEnabled());
    }

    @Test
    public void testGetJettyHttpPort() {
        System.out.println("testGetJettyPort(): " + RuntimeProperties.INSTANCE.getJettyHttpPort());
        assertEquals(8080, RuntimeProperties.INSTANCE.getJettyHttpPort());
    }

    @Test
    public void testGetJettyHttpsPort() {
        System.out.println("testGetJettyPort(): " + RuntimeProperties.INSTANCE.getJettyHttpsPort());
        assertEquals(8443, RuntimeProperties.INSTANCE.getJettyHttpsPort());
    }

    @Test
    public void testGetJettyHttpsKeystorePath() {
        System.out.println("getJettyHttpsKeystorePath(): " + RuntimeProperties.INSTANCE.getJettyHttpsKeystorePath());
        assertEquals("classpath:/jetty/keystore.jks", RuntimeProperties.INSTANCE.getJettyHttpsKeystorePath());
    }

    @Test
    public void testGetJettyHttpsKeystorePass() {
        System.out.println("getJettyHttpsKeystorePass(): " + RuntimeProperties.INSTANCE.getJettyHttpsKeystorePass());
        assertEquals("snowsnow", RuntimeProperties.INSTANCE.getJettyHttpsKeystorePass());
    }

    @Test
    public void testGetJettyHttpsPrivateKeyAlias() {
        System.out.println("getJettyHttpsPrivateKeyAlias(): " + RuntimeProperties.INSTANCE.getJettyHttpsPrivateKeyAlias());
        assertEquals("localhost", RuntimeProperties.INSTANCE.getJettyHttpsPrivateKeyAlias());
    }

    @Test
    public void testGetJettyHttpsPrivateKeyPass() {
        System.out.println("getJettyHttpsPrivateKeyPass(): " + RuntimeProperties.INSTANCE.getJettyHttpsPrivateKeyPass());
        assertEquals("9wzmbWRkiArs9PKzkX4s", RuntimeProperties.INSTANCE.getJettyHttpsPrivateKeyPass());
    }

    @Test
    public void testGetJettyHttpsTruststorePath() {
        System.out.println("getJettyHttpsTruststorePath(): " + RuntimeProperties.INSTANCE.getJettyHttpsTruststorePath());
        assertEquals("classpath:/jetty/truststore.jks", RuntimeProperties.INSTANCE.getJettyHttpsTruststorePath());
    }

    @Test
    public void testGetJettyHttpsTruststorePass() {
        System.out.println("getJettyHttpsTruststorePass(): " + RuntimeProperties.INSTANCE.getJettyHttpsTruststorePass());
        assertEquals("snowsnow", RuntimeProperties.INSTANCE.getJettyHttpsTruststorePass());
    }

    /**
     * Test of getJettyContextPath method, of class RuntimeProperties.
     */
    @Test
    public void testGetJettyContextPath() {
        System.out.println("getJettyContextPath(): " + RuntimeProperties.INSTANCE.getJettyContextPath());
        assertEquals("/runtime", RuntimeProperties.INSTANCE.getJettyContextPath());
    }

    /**
     * Test of getJettyResourceBase method, of class RuntimeProperties.
     */
    @Test
    public void testGetJettyResourceBase() {
        System.out.println("getJettyResourceBase(): " + RuntimeProperties.INSTANCE.getJettyResourceBase());
        assertEquals("/runtime-webapp", RuntimeProperties.INSTANCE.getJettyResourceBase());
    }
}
