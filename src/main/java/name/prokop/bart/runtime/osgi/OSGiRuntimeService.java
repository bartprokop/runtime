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
package name.prokop.bart.runtime.osgi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import name.prokop.bart.runtime.RuntimeService;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum OSGiRuntimeService implements RuntimeService {

    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(OSGiRuntimeService.class);
    private final FrameworkFactory frameworkFactory;
    private Framework framework;

    private OSGiRuntimeService() {
        try {
            frameworkFactory = getFrameworkFactory();
        } catch (Exception e) {
            logger.error("Unable to get Framework Factory for OSGi", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        framework = frameworkFactory.newFramework(null);
        try {
            framework.init();
            framework.start();
        } catch (BundleException e) {
            logger.error("Unable to start OSGi", e);
        }
    }

    @Override
    public void stop() {
        try {
            framework.stop();
            framework.waitForStop(1500L);
        } catch (BundleException | InterruptedException e) {
            logger.error("Unable to stop OSGi", e);
        }
    }

    @Override
    public String toString() {
        return "OSGi Runtime service";
    }

    private static FrameworkFactory getFrameworkFactory() throws Exception {
        java.net.URL url = OSGiRuntimeService.class.getClassLoader().getResource("META-INF/services/org.osgi.framework.launch.FrameworkFactory");
        if (url != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                for (String s = br.readLine(); s != null; s = br.readLine()) {
                    s = s.trim();
                    // Try to load first non-empty, non-commented line.
                    if ((s.length() > 0) && (s.charAt(0) != '#')) {
                        return (FrameworkFactory) Class.forName(s).newInstance();
                    }
                }
            }
        }

        throw new Exception("Could not find framework factory.");
    }
}
