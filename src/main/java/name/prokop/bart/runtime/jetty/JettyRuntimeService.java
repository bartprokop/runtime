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
package name.prokop.bart.runtime.jetty;

import name.prokop.bart.runtime.RuntimeProperties;
import name.prokop.bart.runtime.RuntimeService;
import name.prokop.bart.runtime.spring.SpringRuntimeService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum JettyRuntimeService implements RuntimeService {

    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(JettyRuntimeService.class);
    private Server server;

    private JettyRuntimeService() {
        logger.info("Instantializing " + this);
    }

    @Override
    public void start() {
        if (!RuntimeProperties.INSTANCE.isJettyEnabled()) {
            logger.info("Jetty service disabled, doing nothing.");
            return;
        }

        logger.info("Starting " + this);
        server = new Server();

        if (RuntimeProperties.INSTANCE.isJettyHttpEnabled()) {
            Connector connector = new SelectChannelConnector();
            connector.setPort(RuntimeProperties.INSTANCE.getJettyHttpPort());
            server.addConnector(connector);
        }

        if (RuntimeProperties.INSTANCE.isJettyHttpsEnabled()) {
            SslSelectChannelConnector connector = new SslSelectChannelConnector(new RuntimeSslContextFactory());
            connector.setPort(RuntimeProperties.INSTANCE.getJettyHttpsPort());
            connector.setReuseAddress(true);
            server.addConnector(connector);
        }

        server.setHandler(buildHandler());

        try {
            server.start();
        } catch (Exception e) {
            logger.error("Cannot start " + this, e);
            server = null;
        }
    }

    private Handler buildHandler() {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(RuntimeProperties.INSTANCE.getJettyContextPath());
        webAppContext.setBaseResource(Resource.newClassPathResource(RuntimeProperties.INSTANCE.getJettyResourceBase()));

        GenericWebApplicationContext springContext = new GenericWebApplicationContext();
        springContext.setServletContext(webAppContext.getServletContext());
        springContext.setParent(SpringRuntimeService.INSTANCE.getApplicationContext());
        springContext.refresh();

        webAppContext.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, springContext);
        return webAppContext;
    }

    @Override
    public void stop() {
        if (!RuntimeProperties.INSTANCE.isJettyEnabled()) {
            logger.info("Jetty service disabled, doing nothing.");
            return;
        }
        logger.info("Stopped " + this);
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Cannot stop " + this, e);
        } finally {
            server = null;
        }
    }

    @Override
    public String toString() {
        return "Runtime Jetty Service";
    }

    /**
     * Returns Jetty server instance that is behind Jetty Service
     *
     * @return Jetty server instance
     */
    public Server getServer() {
        return server;
    }
}
