package lab.Commands.ConcreteCommands;

import lab.Commands.Command;
import lab.Commands.CommandReceiver;
import lab.Commands.SerializedCommands.Message;

import java.io.IOException;

public class FilterContainsName extends Command {
    private static final long serialVersionUID = 33L;
    transient private CommandReceiver commandReceiver;

    public FilterContainsName (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public FilterContainsName() {}

    @Override
    protected Message execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            return commandReceiver.filter_contains_name(args[1]);
        }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
        return null;
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда filter_contains_name. Синтаксис: filter_contains_name имя – выводит элементы, значения поля name которых содержит заданную подстроку.");
    }
}
