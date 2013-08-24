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
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import name.prokop.bart.runtime.spring.service.SpringRuntimeServiceInfo;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This is part of Jetty in Runtime example web app
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public class WebInfo extends HttpServlet {

    /**
     * Just hit fulfill
     */
    private static final long serialVersionUID = -6421575599578347530L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            out.println(info1());


            out.println("WEB Server information");
            out.println();
            out.println("\n*** system properties ***");
            out.println("java.version: " + System.getProperty("java.version"));
            out.println("java.vendor: " + System.getProperty("java.vendor"));
            out.println("java.vendor.url: " + System.getProperty("java.vendor.url"));
            out.println("java.home: " + System.getProperty("java.home"));
            out.println("java.vm.specification.version: " + System.getProperty("java.vm.specification.version"));
            out.println("java.vm.specification.vendor: " + System.getProperty("java.vm.specification.vendor"));
            out.println("java.vm.specification.name: " + System.getProperty("java.vm.specification.name"));
            out.println("java.vm.version: " + System.getProperty("java.vm.version"));
            out.println("java.vm.vendor: " + System.getProperty("java.vm.vendor"));
            out.println("java.vm.name: " + System.getProperty("java.vm.name"));
            out.println("java.specification.version: " + System.getProperty("java.specification.version"));
            out.println("java.specification.vendor: " + System.getProperty("java.specification.vendor"));
            out.println("java.specification.name: " + System.getProperty("java.specification.name"));
            out.println("java.class.version: " + System.getProperty("java.class.version"));
            out.println("java.class.path: " + System.getProperty("java.class.path"));
            out.println("java.library.path: " + System.getProperty("java.library.path"));
            out.println("java.io.tmpdid: " + System.getProperty("java.io.tmpdir"));
            out.println("java.compiler: " + System.getProperty("java.compiler"));
            out.println("java.ext.dirs: " + System.getProperty("java.ext.dirs"));
            out.println("os.name: " + System.getProperty("os.name"));
            out.println("os.arch: " + System.getProperty("os.arch"));
            out.println("os.version: " + System.getProperty("os.version"));
            out.println("file.separator: " + System.getProperty("file.separator"));
            out.println("path.separator: " + System.getProperty("path.separator"));
            out.println("line.separator: " + System.getProperty("line.separator"));
            out.println("user.dir  : " + System.getProperty("user.dir"));
            out.println("user.home : " + System.getProperty("user.home"));
            out.println("user.name: " + System.getProperty("user.name"));
            out.println();
            out.println("\n*** request variable ***");
            out.println("request.getContextPath: " + request.getContextPath());
            out.println("request.getLocalAddr: " + request.getLocalAddr());
            out.println("request.getLocalName: " + request.getLocalName());
            out.println("request.getLocalPort: " + request.getLocalPort());
            out.println("request.getPathInfo: " + request.getPathInfo());
            out.println("request.getPathTranslated: " + request.getPathTranslated());
            out.println("request.getProtocol: " + request.getProtocol());
            out.println("request.getRemoteAddr: " + request.getRemoteAddr());
            out.println("request.getRemoteHost: " + request.getRemoteHost());
            out.println("request.getRemotePort: " + request.getRemotePort());
            out.println("request.getRemoteUser: " + request.getRemoteUser());
            out.println("request.getRequestURI: " + request.getRequestURI());
            out.println("request.getRequestedSessionId: " + request.getRequestedSessionId());
            out.println("request.getScheme: " + request.getScheme());
            out.println("request.getServerName: " + request.getServerName());
            out.println("request.getServerPort: " + request.getServerPort());
            out.println("request.getServletPath: " + request.getServletPath());
            out.println("request.getUserPrincipal: " + request.getUserPrincipal());
            out.println();
            out.println("\n*** this ***");
            out.println("this.getServletInfo: " + this.getServletInfo());
            out.println("this.getServletName: " + this.getServletName());
            out.println("this.getServletName: " + this.getServletContext().getClass());
            out.println();
            out.println("\n*** parameter map ***");
            @SuppressWarnings("rawtypes")
            Map parameterMap = request.getParameterMap();
            for (Object key : parameterMap.keySet()) {
                out.print(key + " = ");
                String[] values = (String[]) parameterMap.get(key);
                for (String s : values) {
                    out.print(s + ", ");
                }
                out.println();
            }
        } catch (Exception e) {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private String info1() {
        StringBuilder sb = new StringBuilder();

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        sb.append("**** Spring information **** \n");
        sb.append("WebApplicationContext.getClass(): ").append(wac.getClass()).append("\n");
        sb.append("WebApplicationContext.this: ").append(wac).append("\n");
        sb.append("** SpringRuntimeServiceInfo bean **\n");
        SpringRuntimeServiceInfo bean = wac.getBean(SpringRuntimeServiceInfo.class);
        sb.append("WebApplicationContext.getBean(SpringRuntimeServiceInfo.class): ").append(bean).append("\n");
        sb.append("bean.getClass(): ").append(bean.getClass()).append("\n");
        sb.append("bean.hello(): ").append(bean.hello()).append("\n");
        sb.append("bean.getApplicationContext().getClass(): ").append(bean.getApplicationContext().getClass()).append("\n");
        sb.append("bean.getApplicationContext().this: ").append(bean.getApplicationContext()).append("\n");
        //        sb.append("WebApplicationContext.getClass(): " + wac.getClass() + "\n");

        return sb.toString();
    }
}
