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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is part of Jetty in Runtime example web app
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doIt(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doIt(req, resp);
    }

    protected void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            out.println("Hello world !!!");
            out.println();

            out.println("=== The headers passed are ===");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String nextElement = headerNames.nextElement();
                out.println(nextElement + ": " + request.getHeader(nextElement));
            }
            out.println();

            out.println("=== The parameters passed are ===");
            @SuppressWarnings("rawtypes")
            Map parameterMap = request.getParameterMap();
            for (Object key : parameterMap.keySet()) {
                out.print(key + " = ");
                String[] values = (String[]) parameterMap.get(key);
                for (int i = 0; i < values.length; i++) {
                    out.print(values[i]);
                    if (i < values.length - 1) {
                        out.print(", ");
                    }
                }
                out.println();
            }
            out.println();

            out.println("=== The request content ===");
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
