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
package name.prokop.bart.runtime.spring;

import name.prokop.bart.runtime.RuntimeProperties;
import name.prokop.bart.runtime.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class is responsible for creating Spring context for Runtime Engine. The
 * context definition must start its origin in /META-INF/runtime-context.xml. We
 * use "classpath*:/META-INF/runtime-context.xml" to load and merge all context
 * definitions, thus allowing multiple contexts definition (one per each
 * module).
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum SpringRuntimeService implements RuntimeService {

    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(SpringRuntimeService.class);
    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    private SpringRuntimeService() {
        logger.info("Instantializing " + this);
    }

    @Override
    public synchronized void start() {
        logger.info("Starting " + this);
        logger.info("Expecting the Spring context in: {}", RuntimeProperties.INSTANCE.getSpringContext());
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext(RuntimeProperties.INSTANCE.getSpringContext());
        logger.info("Spring context initialized");
    }

    @Override
    public synchronized void stop() {
        classPathXmlApplicationContext.close();
        logger.info("Stopped " + this);
    }

    @Override
    public String toString() {
        return "Runtime Spring Service";
    }

    public synchronized ApplicationContext getApplicationContext() {
        return classPathXmlApplicationContext;
    }
}
