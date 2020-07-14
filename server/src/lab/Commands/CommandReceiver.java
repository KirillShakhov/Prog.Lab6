package lab.Commands;


import lab.BD;
import lab.BasicClasses.Album;
import lab.BasicClasses.MusicBand;
import lab.Commands.SerializedCommands.Message;
import lab.Utils.Validator;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Ресивер(получатель), отправляет серилизованные объекты на сервер.
 */
public class CommandReceiver {
    public CommandReceiver() {

    }

    public String info() throws IOException {
        System.out.println("Клиенту отправлен результат работы команды INFO");
        return "Коллекция типа: ArrayList\nДата инициализации: "+BD.getCreateTime()+"\nКоличество элементов: "+BD.size();
        //return "Коллекция типа: ArrayList\nДата инициализации: ";
    }

    public String show() throws IOException {
        BD.sort();
        String result = "";
        if(BD.size() != 0) {
            for (int i = 0; i < BD.size(); i++) {
                result = result + BD.get(i).toString() + "\n";
            }
        }
        else{
            result = ("Элементы отсутствуют");
        }
        System.out.println("Клиенту отправлен результат работы команды SHOW");
        return result;
    }

    public String add(MusicBand o) throws IOException {
        o.setID((long) BD.giveID());
        o.setCreationDate(LocalDateTime.now());
        if (Validator.validateMusicBand(o)) {
            if(BD.add(o)){
                return "Элемент добавлен в коллекцию.";
            }
            else{
                return "Элемент не добавлен в коллекцию.";
            }
        } else {
            return "Полученный элемент не прошел валидацию на стороне сервера.";
        }
    }

    /**
     *
     */
    public String update(Message mes) throws IOException {
        Integer groupId;
        try {
            groupId = Integer.parseInt(mes.getArgs());
            if (BD.checkExist(groupId)) {
                if (Validator.validateMusicBand(mes.getMusicBand())) {
                    MusicBand o = mes.getMusicBand();
                    o.setID((long)groupId);
                    o.setCreationDate(LocalDateTime.now());
                    BD.update(o, groupId);
                    return "Команда update выполнена.";
                } else {
                    return "Полученный элемент не прошел валидацию на стороне сервера.";
                }
            }
            else {return "Элемента с таким ID нет в коллекции.";}
        } catch (NumberFormatException e) {
            return "Команда не выполнена. Вы ввели некорректный аргумент.";
        }
    }

    /**
     *
     * @param ID - удаление по ID.
     */
    public String removeById(String ID) throws IOException {
        Integer groupId;
        try {
            groupId = Integer.parseInt(ID);
            if (BD.checkExist(groupId)) {
                BD.remove(groupId);
                return "Элемент с ID " + groupId + " успешно удален из коллекции.";
            } else {
                return "Элемента с таким ID нет в коллекции.";
            }
        } catch (NumberFormatException e) {
            return "Команда не выполнена. Вы ввели некорректный аргумент.";
        }
    }

    public String removeByDescription(String description) throws IOException {
        int j = 0;
        for(int i = 0; i < BD.size(); i++){
            if(BD.get(i).getDescription().equals(description)){
                BD.remove(i);
                j++;
            }
        }
        if(j == 0) {
            return "Таких элементов нет";
        }
        else{
            return "Удалено " + j + " объектов";
        }
    }

    public String  clear() throws IOException {
        BD.clean();

        return "Коллекция успешно очищена.";
    }



    public String removeLower(MusicBand musicBand) throws IOException {

        if (Validator.validateMusicBand(musicBand)) {
            return BD.removeLower(musicBand);
        } else {
            return "Полученный элемент не прошел валидацию на стороне сервера.";
        }
    }

    public String  filterContainsName(String arg) throws IOException {
        String result = "";
        int j = 0;
        for (int i = 0; i < BD.size(); i++) {
            if (BD.get(i).getName().contains(arg)) {
                result += BD.get(i).toString() + "\n";
                j++;
            }
        }
        if (j != 0) {
            return result;
        } else { return "Таких элементов нет";}
    }

    public String countGreaterThanBestAlbum(Album musicBand) throws IOException {
        String result = "";
        int j = 0;
        for(int i = 0; i < BD.size(); i++){
            if(BD.get(i).getSales() > musicBand.getSales()){ j++; }
        }
        if (j != 0) {
            return j+" элементов";
        } else { return "Таких элементов нет";}
    }

    public String reorder() throws IOException {
        BD.reverse = !BD.reverse;
        BD.sort();
        return "Коллекция успешно отсортирована.";
    }

    public String removeGreater(MusicBand mb) {
        if (Validator.validateMusicBand(mb)) {
            return BD.removeGreater(mb);
        } else {
            return "Полученный элемент не прошел валидацию на стороне сервера.";
        }
    }
}
