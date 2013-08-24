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
package name.prokop.bart.runtime.logger;

import name.prokop.bart.runtime.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum LoggerRuntimeService implements RuntimeService {

    INSTANCE;
    private final Logger logger;

    private LoggerRuntimeService() {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        System.out.println("Standard out redirected to slf4j (this line shall be in logs not on STD-OUT)");
        System.err.println("Standard err redirected to slf4j (this line shall be in logs not on STD-ERR)");
        logger = LoggerFactory.getLogger(LoggerRuntimeService.class);
        logger.info("Instantializing " + this);
    }

    @Override
    public void start() {
        logger.info("Starting " + this);
    }

    @Override
    public void stop() {
        logger.info("Stopped " + this);
    }

    @Override
    public final String toString() {
        return "Runtime Logger Service";
    }
}
