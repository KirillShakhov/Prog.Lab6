package lab.Commands.ConcreteCommands;


import lab.Commands.Command;
import lab.Commands.CommandReceiver;
import lab.Commands.SerializedCommands.Message;

import java.io.IOException;

/**
 * Конкретная команда выполнения скрипта.
 */
public class ExecuteScript extends Command {
    private CommandReceiver commandReceiver;
    private static String path;

    public ExecuteScript(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public ExecuteScript() {}

    @Override
    protected Message execute(String[] args) throws StackOverflowError, IOException {
        try {
            if (args.length == 2) { path = args[1];
            commandReceiver.executeScript(args[1]);
            }
            else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
        } catch (StackOverflowError ex) {
            System.out.println("Ошибка! Обнаружен выход за пределы стека.");
        }
        return null;
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда execute_script. Синтаксис: execute_script file_name – считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    public static String getPath() {
        return path;
    }
}
