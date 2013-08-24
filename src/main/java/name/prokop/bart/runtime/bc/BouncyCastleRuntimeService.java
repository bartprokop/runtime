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
package name.prokop.bart.runtime.bc;

import java.security.Provider;
import java.security.Security;
import name.prokop.bart.runtime.RuntimeService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public enum BouncyCastleRuntimeService implements RuntimeService {

    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(BouncyCastleRuntimeService.class);

    private BouncyCastleRuntimeService() {
        logger.info("Registering Bouncy Caste Security Provider");
        Security.addProvider(new BouncyCastleProvider());
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            logger.info("Security provider detected: {}", provider);
        }
    }

    public void start() {
    }

    public void stop() {
        logger.info("Removing BC security provider");
        Security.removeProvider("BC");
    }
}
