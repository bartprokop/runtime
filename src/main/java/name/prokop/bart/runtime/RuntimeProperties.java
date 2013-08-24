/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package name.prokop.bart.runtime;

import java.util.Properties;

/**
 * This singleton represents the Runtime Properties. They are accessible via
 * standard POJO convention.
 *
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum RuntimeProperties {

    INSTANCE;
    private final Properties properties = new Properties();

    private RuntimeProperties() {
        init();
    }

    private void init() {
        // Load defaults
        try {
            properties.load(RuntimeProperties.class.getResourceAsStream("logger/default.properties"));
            properties.load(RuntimeProperties.class.getResourceAsStream("spring/default.properties"));
            properties.load(RuntimeProperties.class.getResourceAsStream("jetty/default.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Runtime build broken !!!", e);
        }

        // override with /META-INF/runtime.properties
        try {
            if (RuntimeProperties.class.getResource("/META-INF/runtime.properties") != null) {
                properties.load(RuntimeProperties.class.getResourceAsStream("/META-INF/runtime.properties"));
            }
        } catch (Exception e) {
        }
    }

    /**
     * Creates shallow copy of all runtime properties
     *
     * @return shallow copy of all properties
     */
    public final Properties getProperties() {
        return (Properties) properties.clone();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Convenient getter starts here - LOGGER
    ////////////////////////////////////////////////////////////////////////////
    public String getLoggerDirectory() {
        return properties.getProperty("runtime.logger.directory");
    }

    public String getLoggerFile() {
        return properties.getProperty("runtime.logger.file");
    }

    public String getLoggerRollingPattern() {
        return properties.getProperty("runtime.logger.rollingPattern");
    }

    ////////////////////////////////////////////////////////////////////////////
    // Convenient getter starts here - SPRING
    ////////////////////////////////////////////////////////////////////////////
    public String getSpringContext() {
        return properties.getProperty("runtime.spring.context");
    }

    ////////////////////////////////////////////////////////////////////////////
    // Convenient getter starts here
    ////////////////////////////////////////////////////////////////////////////
    /**
     * @return if Jetty Service shall be enabled on runtime startup
     */
    public boolean isJettyEnabled() {
        return Boolean.parseBoolean(properties.getProperty("runtime.jetty.enabled"));
    }

    /**
     * @return if HTTP connector shall be used
     */
    public boolean isJettyHttpEnabled() {
        return Boolean.parseBoolean(properties.getProperty("runtime.jetty.http.enabled"));
    }

    /**
     * @return TCP listening port for HTTP
     */
    public int getJettyHttpPort() {
        return Integer.parseInt(properties.getProperty("runtime.jetty.http.port"));
    }

    /**
     * @return if HTTPS connector shall be used
     */
    public boolean isJettyHttpsEnabled() {
        return Boolean.parseBoolean(properties.getProperty("runtime.jetty.https.enabled"));
    }

    public int getJettyHttpsPort() {
        return Integer.parseInt(properties.getProperty("runtime.jetty.https.port"));
    }

    public String getJettyHttpsKeystorePath() {
        return properties.getProperty("runtime.jetty.https.keystore.path");
    }

    public String getJettyHttpsKeystorePass() {
        return properties.getProperty("runtime.jetty.https.keystore.pass");
    }

    public String getJettyHttpsPrivateKeyAlias() {
        return properties.getProperty("runtime.jetty.https.private.key.alias");
    }

    public String getJettyHttpsPrivateKeyPass() {
        return properties.getProperty("runtime.jetty.https.private.key.pass");
    }

    public boolean getJettyHttpsClientAuth() {
        return Boolean.parseBoolean(properties.getProperty("runtime.jetty.https.client.auth"));
    }

    public String getJettyHttpsTruststorePath() {
        return properties.getProperty("runtime.jetty.https.truststore.path");
    }

    public String getJettyHttpsTruststorePass() {
        return properties.getProperty("runtime.jetty.https.truststore.pass");
    }

    public String getJettyContextPath() {
        return properties.getProperty("runtime.jetty.contextPath");
    }

    public String getJettyResourceBase() {
        return properties.getProperty("runtime.jetty.resourceBase");
    }
}
