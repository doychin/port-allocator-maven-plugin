import java.io.IOException;
import java.net.ServerSocket;

import junit.framework.TestCase;

public class TestClass1 extends TestCase {

    public void testMethod() throws IOException, InterruptedException {
        String buildPortStr = System.getProperty("build.port");
        assertNotNull(buildPortStr);
        assertFalse(buildPortStr.length() == 0);

        int buildPort = Integer.parseInt(buildPortStr);

        final ServerSocket serverSocket = new ServerSocket(buildPort);
        try {
            Thread.sleep(1000);
            new Thread(new Runnable() {

                public void run() {
                    try {
                        serverSocket.accept();
                    } catch (IOException ignored) {
                    }
                }
            }).start();
        } finally {
            serverSocket.close();
        }
    }
}