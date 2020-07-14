package lab.Commands.ConcreteCommands;


import lab.Commands.Command;
import lab.Commands.CommandReceiver;
import lab.Commands.SerializedCommands.Message;

import java.io.IOException;
import java.io.Serializable;

/**
 * Конкретная команда получения информации о коллекции.
 */
public class Reorder extends Command implements Serializable {
    private static final long serialVersionUID = 33L;
    transient private CommandReceiver commandReceiver;

    public Reorder() {}

    public Reorder (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected Message execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде info.");
        }
        return commandReceiver.reorder();
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда reorder – отсортировать коллекцию в обратном порядке.");
    }
}
