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
    transient private CommandReceiver commandReceiver;

    public RemoveByDescription (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public RemoveByDescription() {}

    @Override
    protected Message execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            return commandReceiver.removeByDescription(args[1]);
        }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
        return null;
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда remove_by_description. Синтаксис: remove_by_description описание – удалить элемент из коллекции по его описанию.");
    }
}
