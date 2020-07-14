package lab.Commands;

import lab.BasicClasses.Album;
import lab.BasicClasses.MusicBand;
import lab.Commands.ConcreteCommands.*;
import lab.Commands.SerializedCommands.Message;
import lab.Commands.Utils.Creaters.ElementCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Ресивер(получатель), отправляет серилизованные объекты на сервер.
 */
public class CommandReceiver {
    private final CommandInvoker commandInvoker;
    private final ElementCreator elementCreator;

    public CommandReceiver(CommandInvoker commandInvoker, ElementCreator elementCreator) {
        this.commandInvoker = commandInvoker;
        this.elementCreator = elementCreator;
    }

    public Message help() {
        commandInvoker.getCommandMap().forEach((name, command) -> command.writeInfo());
        return null;
    }

    public Message info() throws IOException, ClassNotFoundException, InterruptedException {
        //sender.sendObject(new Message("Хей"));
        ////Thread.sleep(delay);
        return new Message(new Info());
    }

    public Message show() throws IOException, ClassNotFoundException, InterruptedException {
        //sender.sendObject(new Message(new Show()));
        ////Thread.sleep(delay);
        return new Message(new Show());
    }

    public Message add() throws IOException, InterruptedException, ClassNotFoundException {
        return new Message(new Add(), elementCreator.createMusicBand());
    }

    /**
     *
     * @param ID - апдейт элемента по ID.
     */
    public Message update(String ID) throws IOException, InterruptedException, ClassNotFoundException {
        //sender.sendObject(new SerializedCombinedCommand(new Update(), elementCreator.createMusicBand(), ID));
        ////Thread.sleep(delay);
        return new Message(new Update(), elementCreator.createMusicBand(), ID);
    }

    /**
     *
     * @param ID - удаление по ID.
     */
    public Message removeById(String ID) throws IOException, InterruptedException, ClassNotFoundException {
        //sender.sendObject(new SerializedArgumentCommand(new RemoveByID(), ID));
        ////Thread.sleep(delay);
        return new Message(new RemoveByID(), ID);
    }

    public Message removeByDescription(String des) throws IOException, InterruptedException, ClassNotFoundException {
        //sender.sendObject(new SerializedArgumentCommand(new RemoveByID(), ID));
        ////Thread.sleep(delay);
        return new Message(new RemoveByDescription(), des);
    }

    public Message clear() throws IOException, InterruptedException, ClassNotFoundException {
        //sender.sendObject(new SerializedSimplyCommand(new Clear()));
        ////Thread.sleep(delay);
        return new Message(new Clear());
    }

    public Message exit() throws IOException {
        System.out.println("Завершение работы клиента.");
        System.exit(0);
        return null;
    }

    public Message filter_contains_name(String arg){
        return new Message(new FilterContainsName(), arg);
    }

    public Message removeGreater() throws IOException, InterruptedException, ClassNotFoundException {
        return new Message(new RemoveGreater(), elementCreator.createMusicBand());
    }

    public Message removeLower() throws IOException, ClassNotFoundException, InterruptedException {
        return new Message(new RemoveLower(), elementCreator.createMusicBand());
    }

    public Message executeScript(String path) {
        /*
        if (path.isEmpty()) {
            System.out.println("Вы не указали имя файла");
        } else {
            try {
                String[] lines = Console.readFile(path).split("\\r?\\n");
                user.addLevel_list("execute_script " + args.get(0));
                for(String line : lines) {
                    if(!user.check_list(line)) {
                        System.out.println(">" + line);
                        user.update(line);
                    }
                    else{
                        System.out.println("Файл вызывает сам себя");
                    }
                }
                user.remove_list();

            }
            catch (FileNotFoundException e) {
                System.out.println("Файл отсутствует или нет прав на чтение.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
         */

        String line;
        String command;
        ArrayList<String> parameters = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.split(" ")[0].matches("add|update|remove_lower|remove_greater")) {
                    parameters.clear();
                    command = line;
                    for (int i = 0; i < 11; i++) {
                        if (line != null) {
                            line = bufferedReader.readLine();
                            parameters.add(line);
                        } else { System.out.println("Не хватает параметров для создания объекта."); break; }
                    }
                    MusicBand musicBand = elementCreator.createScriptMusicBand(parameters);
                    if (musicBand != null) {
                        switch (command.split(" ")[0]) {
                            case "add":
                                //sender.sendObject(new Message(new Add(), musicBand));
                                //Thread.sleep(delay);
                                //Receiver.receive(socketChannel);
                                break;
                            case "update":
                                //sender.sendObject(new Message(new Update(), elementCreator.createMusicBand(), command.split(" ")[1]));
                                //Thread.sleep(delay);
                                //Receiver.receive(socketChannel);
                                break;
                            case "remove_greater":
                                //sender.sendObject(new Message(new RemoveGreater(), musicBand));
                                //Thread.sleep(delay);
                                //Receiver.receive(socketChannel);
                                break;
                            case "remove_lower":
                                //sender.sendObject(new Message(new RemoveLower(), musicBand));
                                //Thread.sleep(delay);
                                //Receiver.receive(socketChannel);
                                break;
                        }
                    }
                } else if(line.split(" ")[0].equals("count_by_group_admin")) {
                    parameters.clear();
                    for (int i = 0; i < 5; i++) {
                        if (line != null) {
                            line = bufferedReader.readLine();
                            parameters.add(line);
                        } else { System.out.println("Не хватает параметров для создания объекта."); break; }
                    }
                    Album album = elementCreator.createScriptAlbum(parameters);
                    if (album != null) {
                        //sender.sendObject(new SerializedObjectCommand(new CountByGroupAdmin(), album));
                        //Thread.sleep(delay);
                        //Receiver.receive(socketChannel);
                    }
                } else if (line.split(" ")[0].equals("execute_script")
                        && line.split(" ")[1].equals(ExecuteScript.getPath())) { System.out.println("Пресечена попытка рекурсивного вызова скрипта."); }
                else { commandInvoker.executeCommand(line.split(" ")); }
            }
        } catch (IOException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
        return null;

    }

    public Message countGreaterThanBestAlbum() {
        return new Message(new CountGreaterThanBestAlbum(), new MusicBand(elementCreator.createAlbum()));
    }

    public Message reorder() {
        return new Message(new Reorder());
    }

    public Message auth() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя:");
        System.out.print(">");
        String name = null;
        if(scanner.hasNextLine()) {
            name = scanner.nextLine();
        }
        System.out.println("Введите пароль:");
        System.out.print(">");
        String pass = null;
        if(scanner.hasNextLine()) {
            pass = scanner.nextLine();
        }
        return new Message(new Auth(), name+":::"+pass);
    }

    public Message register() {
        Scanner scanner = new Scanner(System.in);
        String name = "";
        String pass = "";
        String pass2 = "";

        System.out.println("Введите имя:");
        System.out.print(">");
        if(scanner.hasNextLine()) {
            name = scanner.nextLine();
        }
        while (true) {
            try {
                System.out.println("Введите пароль:");
                System.out.print(">");
                if (scanner.hasNextLine()) {
                    pass = scanner.nextLine();
                    if (pass.equals("")) {
                        System.out.println("Пароль не может быть пустым");
                        continue;
                    }
                }
                System.out.println("Введите ещё раз пароль:");
                System.out.print(">");
                if (scanner.hasNextLine()) {
                    pass2 = scanner.nextLine();
                    if (pass.equals("")) {
                        System.out.println("Пароль не может быть пустым");
                        continue;
                    }
                }
                if (pass.equals(pass2)) {
                    break;
                }
            } catch (Exception ignored) { }
        }
        return new Message(new Register(), name+":::"+pass);
    }
}
