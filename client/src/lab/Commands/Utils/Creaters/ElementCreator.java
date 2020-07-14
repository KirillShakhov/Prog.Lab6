package lab.Commands.Utils.Creaters;

import lab.BasicClasses.Album;
import lab.BasicClasses.Coordinates;
import lab.BasicClasses.MusicBand;
import lab.BasicClasses.MusicGenre;
import lab.Commands.Utils.Readers.EnumReaders.GenreReader;
import lab.Commands.Utils.Readers.NotPrimitiveReaders.DateReader;
import lab.Commands.Utils.Readers.PrimitiveAndReferenceReaders.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


/**
 * Классб содержащий методы для создания группы и человека.
 */
public class ElementCreator {

    public MusicBand createMusicBand() {
        String name = StringReader.read("Введите имя группы: ", false);
        double x = PrimitiveDoubleReader.read("Введите X: ", -687, "MIN");
        Float y = PrimitiveFloatReader.read("Введите Y: ", 0, "NULL");
        int numberOfParticipants = PrimitiveIntReader.read("Введите количество участников: ", 0, "MIN");
        String description = StringReader.read("Введите описание: ", false);
        Date establishmentDate = DateReader.read("Пример даты: 2009-12-31\nМожно оставить пустое поле.\nУкажите дату:", true);
        MusicGenre genre = GenreReader.read("Введите жанр:", false);


        //private String name; //Поле не может быть null, Строка не может быть пустой
        //private Coordinates coordinates; //Поле не может быть null
        //private double x; //Значение поля должно быть больше -687
        //private Float y; //Поле не может быть null
        ///private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        //private int numberOfParticipants; //Значение поля должно быть больше 0
        //private String description; //Поле не может быть null
        //private Date establishmentDate; //Поле может быть null
        //private MusicGenre genre; //Поле не может быть null
        //private Album bestAlbum; //Поле может быть null
        //private String name; //Поле не может быть null, Строка не может быть пустой
        //private int tracks; //Значение поля должно быть больше 0
        //private Integer length; //Поле не может быть null, Значение поля должно быть больше 0
        //private int sales; //Значение поля должно быть больше 0

        return new MusicBand(0L , name, new Coordinates(x, y), LocalDateTime.now(), numberOfParticipants, description, establishmentDate, genre, createAlbum());
    }

    public Album createAlbum() {
        String name_album = StringReader.read("Введите имя альбома: ", false);
        int tracks = PrimitiveIntReader.read("Введите количество треков: ", 0, "MIN");
        Integer length = RefIntReader.read("Введите длину альбома: ", false, 0, "MIN");
        int sales = PrimitiveIntReader.read("Введите количество продаж: ", 0, "MIN");
        return new Album(name_album, tracks, length, sales);
    }

    public MusicBand createScriptMusicBand(ArrayList<String> parameters) {
        if (validateArrayMusicBand(parameters)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return new MusicBand(0L,parameters.get(0),
                        new Coordinates(Double.parseDouble(parameters.get(1)), Float.parseFloat(parameters.get(2))),
                        LocalDateTime.now(),
                        Integer.parseInt(parameters.get(3)),
                        parameters.get(4),
                        format.parse(parameters.get(5)),
                        Enum.valueOf(MusicGenre.class, parameters.get(6)),
                        new Album(parameters.get(7), Integer.parseInt(parameters.get(8)), Integer.parseInt(parameters.get(9)), Integer.parseInt(parameters.get(10))));
            } catch (ParseException e) {
                System.out.println("Один из параметров не соответствует требованиям.");
            }
        } else { System.out.println("Один из параметров не соответствует требованиям."); }

        return null;
    }

    public Album createScriptAlbum(ArrayList<String> parameters) {
        if (validateArrayAlbum(parameters)) {
            return new Album(parameters.get(0), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)));
        } else { System.out.println("Один из параметров не соответствует требованиям."); }

        return null;
    }

    private boolean validateArrayMusicBand(ArrayList<String> parameters) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return (!parameters.get(0).isEmpty()
                    && Double.parseDouble(parameters.get(1)) > -687
                    && Float.parseFloat(parameters.get(2)) > -1000f
                    && Integer.parseInt(parameters.get(3)) > 0
                    && !parameters.get(4).isEmpty()
                    && (parameters.get(5).isEmpty() || !format.parse(parameters.get(5)).toString().equals(""))
                    && GenreReader.checkExist(parameters.get(6))
                    && !parameters.get(7).isEmpty()
                    && Integer.parseInt(parameters.get(8)) > 0
                    && Integer.parseInt(parameters.get(9)) > 0
                    && Integer.parseInt(parameters.get(10)) > 0);
        } catch (NumberFormatException | ParseException ex) { return false; }
    }

    private boolean validateArrayAlbum(ArrayList<String> parameters) {
        try {
            return (!parameters.get(0).isEmpty()
                    && Integer.parseInt(parameters.get(1)) > 0
                    && Integer.parseInt(parameters.get(2)) > 0
                    && Integer.parseInt(parameters.get(3)) > 0);

        } catch (NumberFormatException ex) { return false; }
    }
}
