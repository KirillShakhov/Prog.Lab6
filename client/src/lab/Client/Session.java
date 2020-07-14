package lab.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class Session {
    private SocketChannel socketChannel;
    private String hostName;
    private int port;

    public Session(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void startSession() throws IOException, InterruptedException {
        for (int i = 0; true; i++){
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, port);
                socketChannel = SocketChannel.open(inetSocketAddress);
                socketChannel.configureBlocking(false);

                System.out.println(String.format("Подключение к удаленному адресу %s по порту %d", hostName, port));
                break;
            } catch (SocketException ex) {
                System.out.println("Не удалось подключиться к удаленному адресу...");
                if (i == 2) System.exit(0);
                System.out.println("Попробую снова");
                Thread.sleep(2000);
            }
        }
    }

    public void closeSession() throws IOException {
        if (socketChannel != null) { socketChannel.close(); }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
