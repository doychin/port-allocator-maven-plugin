import java.io.IOException;
import java.net.ServerSocket;

import junit.framework.TestCase;

public class TestClass2 extends TestCase {

    public void testMethod() throws IOException, InterruptedException {
        Thread.sleep(100);
        String buildPortStr = System.getProperty("build.port");
        assertNotNull(buildPortStr);
        assertFalse(buildPortStr.length() == 0);

        int buildPort = Integer.parseInt(buildPortStr);

        final ServerSocket serverSocket = new ServerSocket(buildPort);
        try {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        serverSocket.accept();
                    } catch (IOException ignored) {
                    }
                }
            }).start();
            Thread.sleep(1000);
        } finally {
            serverSocket.close();
        }
    }
}