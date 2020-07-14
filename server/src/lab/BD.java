package lab;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lab.BasicClasses.MusicBand;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Класс - база данных, позволяет проводить операции с базой данных. Без него коллекция не будет работать.
 * @autor Шахов Кирилл Андреевич P3132
 * @version 1.1
 */
public class BD {
    /** Поле, которое хранит путь до файла с базой данных */
    private static String file_path = "data.json";
    /** Колекция, которая используется для представления данных в работающей программе. */
    private static ArrayList<MusicBand> data = new ArrayList<>();
    /** Лист, который хранит историю введённых команд. */
    private static ArrayList<String> history = new ArrayList<>();

    public static boolean reverse = false;

    private static BD bd = null;

    /**
     * Создание базы данных, загрузка данных из прошлой сессии или же создание новой в случае отсутствие прошлых сессий.
     *  @param file_path название файла в котором сохранена или будет сохранена база данных.
     *
     */
    public BD(String file_path) {
        BD.file_path = file_path;
        if(load()){
            System.out.println("Загрузка базы данных успешна");
        }
        else{
            System.out.println("Создана пустая коллекция");
            if(save()){
                System.out.println("Файл создан");
            }
            else{
                System.out.println("Нет доступа к файлу");
                System.exit(0);
            }
        }
    }
    /** Метод, позволяет получить id для нового объекта.
     *
     * @return возвращает int ID
     * */
    public static int giveID(){
        boolean is = false;
        for(int result = 0; result < data.size(); result++){
            for(MusicBand m : data){
                if(m.getID() == result){
                    is = true;
                }
            }
            if(is){
                is = false;
            }
            else{
                return result;
            }
        }
        return data.size();
    }
    /** Метод, позволяющий подметить какой-либо объект по ID.
     * @param id ID объекта, который мы хотим поменять.
     * @param musicBand объект.
     *
     * @return возвращает успешность выполнения метода. true - успех, false - исключение
     * */
    public static boolean update(MusicBand musicBand, Integer id){
        try{
            //TODO проверить
            data.set(id, musicBand);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    /** Метод позволяет сохранить коллекцию в файл, название файла указывалось присоздании объекта.
     *
     * @return возвращает успешность выполнения метода. true - успех, false - исключение
     * */
    public static boolean save(){
        //TODO save

        try(FileWriter writer = new FileWriter(file_path, false))
        {
            Type userListType = new TypeToken<ArrayList<MusicBand>>(){}.getType();
            String json = new Gson().toJson(data, userListType);
            writer.write(json);
            writer.flush();
            return true;
        }
        catch(IOException ex){
            //Console.sendln(ex.getMessage());
            return false;
        }

    }

    public static String removeGreater(MusicBand musicBand) {
        int f = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNumberOfParticipants() > musicBand.getNumberOfParticipants()) {
                data.remove(i);
                f++;
            }
        }
        return String.format("Удалено %s элементов", f);
    }

    /** Метод, позволяет загрузить коллекцию из файла.
     *
     * @return возвращает успешность выполнения метода. true - успех, false - исключение
     * */
    private boolean load(){
        //TODO load

        try {
            Type userListType = new TypeToken<ArrayList<MusicBand>>(){}.getType();
            Scanner in = new Scanner(new File(file_path));
            StringBuffer datas = new StringBuffer();
            while (in.hasNext()) datas.append(in.nextLine()).append("\n");
            data = new Gson().fromJson(datas.toString(), userListType);
            return true;
        }
        catch (FileNotFoundException e){
            return false;
        }


    }
    /** Метод, позволяет добавить объект в коллекцию.
     *
     * @param musicBand Объект.
     *
     * @return возвращает успешность выполнения метода. true - успех, false - исключение
     * */
    public static boolean add(MusicBand musicBand){
        try {
            data.add(musicBand);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    /** Метод, возвращает Историю вводимых команд
     *
     * @return ArrayList<String> - История
     * */
    public ArrayList<String> getHistory() { return history; }
    /** Метод, позволяет удалять объекты из коллекции по ID.
     * ВАЖНО: ID в коллекции начинаються с 1, а не с 0.
     * @param id ID файла, который хотим удалить.
     *
     * @return возвращает успешность выполнения метода. true - успех, false - исключение.
     * */
    public static boolean remove(int id) {
        try {
            data.removeIf(m -> m.getID() == id);
            return true;
        }
        catch (Exception ignored){}
        return false;
    }
    /** Метод, позволяет очищать коллекцию.
     *
     * @return возвращает успешность выполнения метода. true - успех, false - исключение.
     * */
    public static boolean clean(){
        try {
            data = new ArrayList<MusicBand>();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    /** Метод, позволяет получить дату создания файла.
     *
     * @return возвращает String - дата создания.
     * */
    public static String getCreateTime(){
        try {
            //return data.get(0).getCreateTime().toString();
            BasicFileAttributes attr = Files.readAttributes(Paths.get(file_path), BasicFileAttributes.class);
            return attr.creationTime().toString();
        }
        catch (Exception e){
            return "В коллекции нет элементов.";
        }
    }
    /** Метод, позволяет получить объект по его ID.
     * ВАЖНО: ID в коллекции начинаються с 1, а не с 0.
     *
     * @param id ID объекта.
     *
     * @return Объект MusicBand.
     * */
    public static MusicBand get(int id){
        return data.get(id);
    }
    /** Метод, позволяет получить количество элементов в коллекции.
     *
     * @return int - колличество элементов.
     * */
    public static int size(){return data.size();}
    /** Метод, позволяет записать команду в историю.
     *
     * @param command Команда, которую надо записать.
     * */
    public void log(String command) { history.add(command); }
    /** Метод, позволяет отсортировать массив по текущему методу сортировки.*/
    public static void sort(){
        if(!BD.reverse){
            Collections.sort(data, (player2, player1) -> {
                if (player1.getID() < player2.getID()) {
                    return 1;
                } else if (player1.getID() > player2.getID()) {
                    return -1;
                } else {
                    return 0;
                }
            });
        }
        else{
            Collections.sort(data, (player2, player1) -> {
                if (player1.getID() > player2.getID()) {
                    return 1;
                } else if (player1.getID() < player2.getID()) {
                    return -1;
                } else {
                    return 0;
                }
            });
        }
    }

    public static boolean checkExist(Integer groupId) {
        for (MusicBand musicBand:data) {
            if (musicBand.getID() == groupId) {
                return true;
            }
        }
        return false;
    }

    public static String removeLower(MusicBand musicBand) {
        int f = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNumberOfParticipants() < musicBand.getNumberOfParticipants()) {
                data.remove(i);
                f++;
            }
        }
        return String.format("Удалено %s элементов", f);
    }

    public String sha1(String input) {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ignored) {}
        return sha1;
    }
}
