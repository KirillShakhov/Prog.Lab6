package lab.Commands.ConcreteCommands;

import lab.Commands.Command;
import lab.Commands.CommandReceiver;
import lab.Commands.SerializedCommands.Message;

import java.io.IOException;

/**
 * Конкретная команда удаления по ID.
 */
public class RemoveByDescription extends Command {
    private static final long serialVersionUID = 33L;

    @Override
    public String execute(Object argObject) throws IOException {
        String arg = ((Message)argObject).getArgs();
        CommandReceiver commandReceiver = new CommandReceiver();
        return commandReceiver.removeByDescription(arg);
    }
}
