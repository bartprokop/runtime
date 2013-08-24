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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 *
 * @author Bart Prokop <prokop.bart@gmail.com>
 * @version 1.0
 */
public class Pinger implements Runnable {

    private boolean pleaseTerminate = false;
    public static final String TTSPY_SERVER_IP = "65.99.213.252";
    public static final int TTSPY_PORT = 8483; // T, S in dec 

    public static void main() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(TTSPY_PORT);
            datagramSocket.setSoTimeout(1000);

//            String pingerType = RuntimeProperties.INSTANCE.getPingerType();
//            String pingerSerial = RuntimeProperties.INSTANCE.getPingerSerial();
//            String pingerMessage = RuntimeProperties.INSTANCE.getPingerMessage();
            String pingerMessage = "";
            pingerMessage = "[12.0709-000923]" + pingerMessage;

            byte[] buf = ("DEVPING\t" + "\t" + "\t1.1.1.1\t" + pingerMessage).getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, new InetSocketAddress(TTSPY_SERVER_IP, TTSPY_PORT));
            datagramSocket.send(packet);


            byte[] receiveData = new byte[100];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            datagramSocket.receive(receivePacket);
            String datagram = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            System.err.println(datagram);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null) {
                try {
                    datagramSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        long counter = 0;
        while (!pleaseTerminate) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                break;
            }
            counter++;
            if (counter % (10 * 60 * 2) == 0) {
                main();
            }
        }
    }

    public void terminate() {
        pleaseTerminate = true;
    }
}
