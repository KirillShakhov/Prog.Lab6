package lab;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            ClientController clientController = new ClientController("127.0.0.1", args[0]);
            clientController.run();
        }
        else if (args.length == 2){
            ClientController clientController = new ClientController(args[0], args[1]);
            clientController.run();
        }
        else {
            ClientController clientController = new ClientController("127.0.0.1", "3030");
            clientController.run();
        }
    }
}