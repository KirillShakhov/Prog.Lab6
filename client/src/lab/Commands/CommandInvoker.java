package lab.Commands;

import lab.Commands.SerializedCommands.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Инвокер(вызыватель), выполяет команды. Хранит зарегистрированные команды.
 */
public class CommandInvoker {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public Message executeCommand(String[] commandName) {
        try {
            if (commandName.length > 0 && !commandName[0].equals("")) {
                Command command = commandMap.get(commandName[0]);
                return command.execute(commandName);
            } else { System.out.println("Вы не ввели команду."); }
        } catch (NullPointerException ex) {
            System.out.println("Не существует команды " + commandName[0] + ". Для справки используйте – help");
        } catch (IllegalStateException | IOException | ClassNotFoundException | InterruptedException ex) {
            if (ex.getMessage().equals("Connection reset by peer")) {
                System.out.println("Там это... Сервер помер, но ты приходи в следующий раз и обязательно сможешь пошалить с коллекцией");
                System.exit(0);
            }
            System.out.println(ex.getMessage());
        }
        return null;
    }

    HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
