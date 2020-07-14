package lab.Commands.SerializedCommands;

import lab.Commands.Command;

import java.io.Serializable;

public class SerializedArgumentCommand implements Serializable {
    private Command command;
    private String arg;

    public SerializedArgumentCommand(Command command, String arg) {
        this.command = command;
        this.arg = arg;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
