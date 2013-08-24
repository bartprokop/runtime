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
package name.prokop.bart.runtime.h2;

import name.prokop.bart.runtime.RuntimeService;
import org.h2.Driver;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum H2RuntimeService implements RuntimeService {

    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(H2RuntimeService.class);
    private Server tcpServer;
    private Server webServer;
    private Driver driver;

    private H2RuntimeService() {
        logger.info("Instantializing " + this);
    }

    public void start() {
        try {
            driver = org.h2.Driver.load();
            tcpServer = Server.createTcpServer();
            webServer = Server.createWebServer();
            tcpServer.start();
            webServer.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot start H2 server", e);
        }
    }

    public void stop() {
        webServer.stop();
        tcpServer.stop();
    }

    @Override
    public String toString() {
        return "Bart H2 Service";
    }

    public Driver getDriver() {
        return driver;
    }

    public Server getTcpServer() {
        return tcpServer;
    }

    public Server getWebServer() {
        return webServer;
    }
}
