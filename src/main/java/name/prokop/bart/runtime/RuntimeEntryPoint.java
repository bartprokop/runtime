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

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public final class RuntimeEntryPoint {

    /**
     * The Java entry point. The main routine is only here so I can also run the
     * application from the command line
     *
     * @param args Command line arguments, all ignored.
     * @throws java.lang.Exception on abnormal termination
     */
    public static void main(String... args) throws Exception {
        System.err.println("type 'stop' followed by Enter key to exit main loop.");
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
            RuntimeEngineDaemon.sleep100ms();
        }

        if (RuntimeEngine.INSTANCE.isRunning()) {
            daemon.stop();
        }
        daemon.destroy();
    }
}
