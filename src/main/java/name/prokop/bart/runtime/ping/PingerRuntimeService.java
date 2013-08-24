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
package name.prokop.bart.runtime.ping;

import name.prokop.bart.runtime.RuntimeService;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum PingerRuntimeService implements RuntimeService {

    INSTANCE;
    private Pinger pinger = new Pinger();

    @Override
    public void start() {
        Thread thread = new Thread(pinger);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void stop() {
        pinger.terminate();
    }

    @Override
    public String toString() {
        return "Bart Ping Service";
    }
    
    
}
