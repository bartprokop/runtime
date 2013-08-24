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

import java.util.Scanner;
import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public class RuntimeEngineDaemon implements Daemon {

    static {
        // make sure that some important initialization goes very very first !!!
        // it basically ensures that constructor of RuntimeEngine is called
        RuntimeEngine.INSTANCE.name();
    }
    private DaemonContext daemonContext = null;
    private final Logger logger = LoggerFactory.getLogger(RuntimeEngineDaemon.class);

    ////////////////////////////////////////////////////////////////////////////
    // Implementing the Daemon interface is not required for Windows but is for Linux
    @Override
    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        this.daemonContext = dc;
        logger.info("Daemon#init called");
        if (daemonContext != null) {
            logger.info("daemonContext: {}", daemonContext);
        }
    }

    @Override
    public void start() throws Exception {
        logger.info("Daemon#start called");
        startRuntimeEngine();
    }

    @Override
    public void stop() throws Exception {
        logger.info("Daemon#stop called");
        stopRuntimeEngine();
    }

    @Override
    public void destroy() {
        logger.info("Daemon#destroy called");
    }
    ////////////////////////////////////////////////////////////////////////////

    public DaemonContext getDaemonContext() {
        return daemonContext;
    }

    private void startRuntimeEngine() {
        logger.info("startRuntimeEngine called");
        RuntimeEngine.INSTANCE.startRuntime();
    }

    private void stopRuntimeEngine() {
        logger.info("stopRuntimeEngine called");
        RuntimeEngine.INSTANCE.stopRuntime();
    }
    ////////////////////////////////////////////////////////////////////////////
    // Windows procsrv handling - static method with start / stop params passed
    // as arguments
    private static RuntimeEngineDaemon windowsService = null;

    /**
     * Static methods called by prunsrv to start/stop the Windows service. Pass
     * the argument "start" to start the service, and pass "stop" to stop the
     * service.
     *
     * @param args Arguments from prunsrv command line
     */
    public static void windowsService(String args[]) {
        String cmd = "start";
        if (args.length > 0) {
            cmd = args[0];
        }

        if ("start".equals(cmd)) {
            if (windowsService != null || RuntimeEngine.INSTANCE.isRunning()) {
                throw new RuntimeException("service already started");
            } else {
                try {
                    windowsService = new RuntimeEngineDaemon();
                    windowsService.init(null);
                    windowsService.start();
                    //wait with further flow for service termination
                    while (RuntimeEngine.INSTANCE.isRunning()) {
                        sleep100ms();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Cannot start service", e);
                }
            }
        }

        if ("stop".equals(cmd)) {
            if (windowsService == null || !RuntimeEngine.INSTANCE.isRunning()) {
                throw new RuntimeException("service already stopped");
            }
            try {
                windowsService.stop();
                windowsService.destroy();
            } catch (Exception e) {
                throw new RuntimeException("Cannot stop service", e);
            }
            windowsService = null;
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    /**
     * The Java entry point. The main routine is only here so I can also run the
     * application from the command line
     *
     * @param args Command line arguments, all ignored.
     */
    public static void cmdLineEntryPoint() throws Exception {
        RuntimeEngineDaemon daemon = new RuntimeEngineDaemon();
        daemon.init(null);
        daemon.start();

        // wait until receive stop command from keyboard
        Scanner sc = new Scanner(System.in);
        System.err.printf("Enter 'stop' to halt: ");
        while (true) {
            if (System.in.available() > 0 && sc.nextLine().toLowerCase().equals("stop")) {
                break;
            }
            if (!RuntimeEngine.INSTANCE.isRunning()) {
                break;
            }
            sleep100ms();
        }

        if (RuntimeEngine.INSTANCE.isRunning()) {
            daemon.stop();
        }
        daemon.destroy();
    }

    private static void sleep100ms() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException iex) {
        }
    }
}
