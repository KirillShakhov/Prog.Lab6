package lab;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0){
            ServerController serverController = new ServerController(args[0]);
            serverController.run();
        }
        else{
            ServerController serverController = new ServerController("3030");
            serverController.run();
        }
    }
}
