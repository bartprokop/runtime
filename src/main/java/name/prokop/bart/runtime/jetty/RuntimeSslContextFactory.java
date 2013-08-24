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
package name.prokop.bart.runtime.jetty;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import name.prokop.bart.runtime.RuntimeProperties;
import name.prokop.bart.runtime.spring.SpringRuntimeService;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public class RuntimeSslContextFactory extends SslContextFactory {

    private final Logger logger = LoggerFactory.getLogger(SslContextFactory.class);
    private final ApplicationContext applicationContext;

    public RuntimeSslContextFactory() {
        this.applicationContext = SpringRuntimeService.INSTANCE.getApplicationContext();

        try {
            setKeyStore(loadRuntimeKeyStore());
            setCertAlias(RuntimeProperties.INSTANCE.getJettyHttpsPrivateKeyAlias());
            setKeyManagerPassword(RuntimeProperties.INSTANCE.getJettyHttpsPrivateKeyPass());
        } catch (Exception e) {
            logger.warn("Cannot set keystore", e);
        }

        try {
            setTrustStore(loadRuntimeTrustStore());
            //setTrustAll(true);
            setNeedClientAuth(RuntimeProperties.INSTANCE.getJettyHttpsClientAuth());
            //setWantClientAuth(true);
        } catch (Exception e) {
            logger.warn("Cannot set truststore", e);
        }
    }

    private KeyStore loadRuntimeKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        Resource resource = applicationContext.getResource(RuntimeProperties.INSTANCE.getJettyHttpsKeystorePath());
        keyStore.load(resource.getInputStream(), RuntimeProperties.INSTANCE.getJettyHttpsKeystorePass().toCharArray());
        return keyStore;
    }

    private KeyStore loadRuntimeTrustStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        Resource resource = applicationContext.getResource(RuntimeProperties.INSTANCE.getJettyHttpsTruststorePath());
        keyStore.load(resource.getInputStream(), RuntimeProperties.INSTANCE.getJettyHttpsTruststorePass().toCharArray());
        return keyStore;
    }
}
