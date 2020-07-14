package lab.Utils.CommandHandler;


import lab.Commands.SerializedCommands.Message;

import java.io.IOException;
import java.net.Socket;

public class Decrypting {
    private final Socket socket;

    public Decrypting(Socket socket) {
        this.socket = socket;
    }

    public void decrypt(Message o) throws IOException {
        /*
        System.out.println("так так ура");
        System.out.println(o.getCommand());
        if (o instanceof Message) {
            Message message = (Message) o;
            //Command command = simplyCommand.getCommand();
            Command arg = "";
            System.out.println("так так");
            command.execute(arg, socket);
        }

         */
        /*
        if (o instanceof SerializedSimplyCommand) {
            SerializedSimplyCommand simplyCommand = (SerializedSimplyCommand) o;
            Command command = simplyCommand.getCommand();
            String arg = "";
            command.execute(arg, socket);
        }

        if (o instanceof Message) {
            Message simplyCommand = (Message) o;
            Command command = simplyCommand.getCommand();
            String arg = "";
            System.out.println("так так");
            command.execute(arg, socket);
        }

        if (o instanceof SerializedArgumentCommand) {
            SerializedArgumentCommand argumentCommand = (SerializedArgumentCommand) o;
            Command command = argumentCommand.getCommand();
            String arg = argumentCommand.getArg();
            command.execute(arg, socket);
        }

        if (o instanceof SerializedObjectCommand) {
            SerializedObjectCommand objectCommand = (SerializedObjectCommand) o;
            Command command = objectCommand.getCommand();
            Object arg = objectCommand.getObject();
            command.execute(arg, socket);
        }

        if (o instanceof SerializedCombinedCommand) {
            SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) o;
            Command command = combinedCommand.getCommand();
            command.execute(combinedCommand, socket);
        }


         */
    }
}
