package lab.ServerSocket;


import lab.BD;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ControllerOld {
    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static ObjectInputStream ooin; // поток чтения из сокета


    public void run(String strPort) throws IOException {
        try {
            try {
                int port = 0;


                try {
                    port = Integer.parseInt(strPort);
                } catch (NumberFormatException ex) {
                    System.out.println("Ошибка! Неправильный формат порта.");
                    BD.save();
                    System.exit(0);
                }

                server = new ServerSocket(port);
                System.out.println("Сервер запущен!");
                while (true) {
                    clientSocket = server.accept();
                    System.out.println("А я все думал, когда же ты появишься: " + clientSocket);
                    try {
                        while (true) {
                            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                            //out.writeObject(new SerializedMessage("qwrqewq"));
                            /*
                            System.out.println("тт1");
                            ooin = new ObjectInputStream(clientSocket.getInputStream());
                            System.out.println("тт2");
                            Decrypting decrypting = new Decrypting(clientSocket);
                            System.out.println("тт3");
                            Message o = (Message) ooin.readObject();
                            System.out.println("так так4");
                            decrypting.decrypt(o);


                             */

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Клиент " + clientSocket + " того, откинулся...");
                    } finally {
                        System.out.println("гг");
                        clientSocket.close();
                        if (ooin != null) { ooin.close(); }
                    }
                }
            } finally {
                if (clientSocket != null) { clientSocket.close(); }
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException |  NullPointerException e) {
            e.printStackTrace();
            System.out.println(String.valueOf(e));
        }
    }

}
