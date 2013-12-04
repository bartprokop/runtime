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

import name.prokop.bart.runtime.bc.BouncyCastleRuntimeService;
import name.prokop.bart.runtime.h2.H2RuntimeService;
import name.prokop.bart.runtime.jetty.JettyRuntimeService;
import name.prokop.bart.runtime.logger.LoggerRuntimeService;
import name.prokop.bart.runtime.os.OperatingSystemRuntimeService;
import name.prokop.bart.runtime.spring.SpringRuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum RuntimeBootstrap {

    INSTANCE;
    /**
     * Please note that this ensures that all array elements are instantialized
     * at enum construction
     */
    private final RuntimeService[] runtimeServices = new RuntimeService[]{
        LoggerRuntimeService.INSTANCE,
        OperatingSystemRuntimeService.INSTANCE,
        BouncyCastleRuntimeService.INSTANCE,
//        H2RuntimeService.INSTANCE,
        SpringRuntimeService.INSTANCE,
        JettyRuntimeService.INSTANCE,
//        OSGiRuntimeService.INSTANCE
    };
    private final Logger logger;

    private RuntimeBootstrap() {
        // do not move it to field declaration !
        // it must be initialized after each of RuntimeService implementation is instantialized.
        // in theory field order initialization shall be enough, but for clarity, I placed it here.
        // see also The Java Language Specification, §12.4.2.
        // http://java.sun.com/docs/books/jls/third_edition/html/execution.html#12.4.2
        logger = LoggerFactory.getLogger(RuntimeBootstrap.class);
    }

    public void start() {
        for (int i = 0; i < runtimeServices.length; i++) {
            startRuntimeService(runtimeServices[i]);
        }
    }

    public void stop() {
        for (int i = runtimeServices.length - 1; i >= 0; i--) {
            stopRuntimeService(runtimeServices[i]);
        }
    }

    private void startRuntimeService(RuntimeService service) {
        long time = System.currentTimeMillis();
        logger.info("Starting: " + service);
        service.start();
        logger.info(service + " started in " + (System.currentTimeMillis() - time) + " ms.");
    }

    private void stopRuntimeService(RuntimeService service) {
        long time = System.currentTimeMillis();
        logger.info("Stopping: " + service);
        service.stop();
        logger.info(service + " stopped in " + (System.currentTimeMillis() - time) + " ms.");
    }

    public RuntimeService[] getRuntimeServices() {
        return runtimeServices;
    }
}
