package lab.ServerSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Controller {
    private static final int BUFFER_SIZE = 1024;
    private static Selector selector = null;


    public void run(){
        try {
            logger("Starting MySelectorExample...");
            InetAddress hostIP = InetAddress.getLocalHost();
            int port = 9999;

            logger(String.format("Trying to accept connections on %s:%d...",
                    hostIP.getHostAddress(), port));
            selector = Selector.open();
            ServerSocketChannel mySocket = ServerSocketChannel.open();
            ServerSocket serverSocket = mySocket.socket();
            InetSocketAddress address = new InetSocketAddress(hostIP, port);
            serverSocket.bind(address);

            mySocket.configureBlocking(false);
            int ops = mySocket.validOps();
            mySocket.register(selector, ops, null);
            while (true) {

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> i = selectedKeys.iterator();

                while (i.hasNext()) {
                    SelectionKey key = i.next();

                    if (key.isAcceptable()) {
                        processAcceptEvent(mySocket, key);
                    } else if (key.isReadable()) {
                        processReadEvent(key);
                    }
                    i.remove();
                }
            }
        } catch (IOException e) {
            logger(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processAcceptEvent(ServerSocketChannel mySocket,
                                           SelectionKey key) throws IOException {

        logger("Connection Accepted...");

        // Accept the connection and make it non-blocking
        SocketChannel myClient = mySocket.accept();
        myClient.configureBlocking(false);

        // Register interest in reading this channel
        myClient.register(selector, SelectionKey.OP_READ);
    }
    private static final ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
    private static ByteBuffer dataByteBuffer = null;
    private static boolean readLength = true;
    private static void processReadEvent(SelectionKey key) {
        logger("Inside processReadEvent...");
        // create a ServerSocketChannel to read the request
        SocketChannel myClient = (SocketChannel) key.channel();
        try {
            if (readLength) {
                myClient.read(lengthByteBuffer);
                if (lengthByteBuffer.remaining() == 0) {
                    readLength = false;
                    dataByteBuffer = ByteBuffer.allocate(lengthByteBuffer.getInt(0));
                    lengthByteBuffer.clear();
                }
            } else {
                myClient.read(dataByteBuffer);
                if (dataByteBuffer.remaining() == 0) {
                    ObjectInputStream ois = null;
                    ois = new ObjectInputStream(new ByteArrayInputStream(dataByteBuffer.array()));

                    Object ret = ois.readObject();
                    // clean up
                    dataByteBuffer = null;
                    readLength = true;
                    logger(ret.toString());
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
        // Set up out 1k buffer to read data into
        ByteBuffer myBuffer = ByteBuffer.allocate(BUFFER_SIZE);

        //ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(myBuffer.array()));
        ObjectInputStream oin = new ObjectInputStream(myClient.socket().getInputStream());
        try {
            Message message = (Message) oin.readObject();
            logger(message.getCommand());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //myClient.read(myBuffer);


         */
        /*
        String data = new String(myBuffer.array()).trim();
        if (data.length() > 0) {
            logger(String.format("Message Received.....: %s\n", data));
            if (data.equalsIgnoreCase("*exit*")) {
                myClient.close();
                logger("Closing Server Connection...");
            }
        }

         */
    }
    public static void logger(String msg) {
        System.out.println(msg);
    }
}
