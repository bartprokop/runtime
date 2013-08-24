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
package name.prokop.bart.runtime.spring.service;

import name.prokop.bart.runtime.RuntimeEngine;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
@Service
public class SpringRuntimeServiceInfoImpl implements SpringRuntimeServiceInfo, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public String hello() {
        return "Hello World!!!";
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void terminate() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ie) {
                }
                RuntimeEngine.INSTANCE.stopRuntime();
            }
        }).start();
    }
}
