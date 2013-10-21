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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import name.prokop.bart.logback.MemoryAppenderAccess;

/**
 * This is part of Jetty in Runtime example web app
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public class BasicHealthCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println(MemoryAppenderAccess.asText());
        writer.println("OK");

        writer.print("The total amount of memory in the Java virtual machine: ");
        writer.println(Runtime.getRuntime().totalMemory() / (1024 * 1024) + "Mb");
        writer.print("The amount of free memory in the Java Virtual Machine: ");
        writer.println(Runtime.getRuntime().freeMemory() / (1024 * 1024) + "Mb");
        writer.print("The maximum amount of memory that the Java virtual machine will attempt to use: ");
        writer.println(Runtime.getRuntime().maxMemory() / (1024 * 1024) + "Mb");
        File[] listRoots = File.listRoots();
        for (File file : listRoots) {
            writer.println("Free space at " + file + " is " + file.getFreeSpace() + " of " + file.getTotalSpace() + ".");
        }
        writer.close();
    }
}
