package lab.Commands.ConcreteCommands;



import lab.Commands.Command;
import lab.Commands.CommandReceiver;
import lab.Commands.SerializedCommands.Message;

import java.io.IOException;

/**
 * Конкретная команда выхода.
 */
public class Exit extends Command {
    private CommandReceiver commandReceiver;

    public Exit (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Exit() {}

    @Override
    protected Message execute(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде exit.");
        }
        return commandReceiver.exit();
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда exit – завершить программу (без сохранения в файл).");
    }
}
