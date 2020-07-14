package lab.Commands.ConcreteCommands;


import lab.Commands.Command;
import lab.Commands.CommandReceiver;
import lab.Commands.SerializedCommands.Message;

import java.io.IOException;

public class CountGreaterThanBestAlbum extends Command {
    private static final long serialVersionUID = 33L;
    transient private CommandReceiver commandReceiver;

    public CountGreaterThanBestAlbum (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public CountGreaterThanBestAlbum() {}

    @Override
    protected Message execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде count_greater_than_bestAlbum.");
        }
        return commandReceiver.countGreaterThanBestAlbum();
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда count_greater_than_bestAlbum. Синтаксис: count_greater_than_bestAlbum – выводит количество элементов, значение поля bestAlbum которых больше заданного.");
    }
}
