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

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum RuntimeEngine {

    INSTANCE;
    private volatile boolean running = false;
    private final long instantializationTime = System.currentTimeMillis();
    private final Logger logger;

    private RuntimeEngine() {
        // ensure that constructor of Bootstrap is called first and is very very first executed code
        RuntimeBootstrap.INSTANCE.name();
        // ensure that logger is initialized AFTER Bootstrap was instantialized
        // do not move it to field declaration
        logger = LoggerFactory.getLogger(RuntimeEngine.class);
    }

    public synchronized void startRuntime() {
        if (running) {
            logger.warn("tried to invoke startRuntime() more that once, possible illegal state!");
            return;
        }
        running = true;
        RuntimeBootstrap.INSTANCE.start();
        logger.info("Runtime Engine started in " + (System.currentTimeMillis() - instantializationTime) + " ms.");
    }

    public synchronized void stopRuntime() {
        if (!running) {
            logger.warn("tried to invoke stopRuntime() more than once, possible illegal state!");
            return;
        }
        RuntimeBootstrap.INSTANCE.stop();
        running = false;
        logger.info("Runtime Engine stopped after " + (System.currentTimeMillis() - instantializationTime) + " ms.");
    }

    public synchronized boolean isRunning() {
        return running;
    }

    @Override
    public String toString() {
        return super.toString() + "; Started: " + new Date(instantializationTime);
    }
}
