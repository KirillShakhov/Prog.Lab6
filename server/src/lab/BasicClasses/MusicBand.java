package lab.BasicClasses;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Объект, который будет сохраняться в базе данных.
 * @autor Шахов Кирилл Андреевич P3132
 * @version 1.1
 */


public class MusicBand implements Comparable<MusicBand>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private String description; //Поле не может быть null
    private Date establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Album bestAlbum; //Поле может быть null

    /**Конструктор создания объекта
     *
     * @param id Long - ID
     * @param name String - Имя
     * @param coordinates Coordinates - Координаты
     * @param createdate LocalDateTime - Дата создания
     * @param numberOfParticipants int - Количество людей в группе
     * @param description String - Описание
     * @param establishmentDate Date - дата создания группы
     * @param genre MusicGenre - жанр(PSYCHEDELIC_ROCK, RAP, POP, POST_ROCK, POST_PUNK)
     * @param bestAlbum Album - лучший альбом
     */
    public MusicBand(Long id, String name, Coordinates coordinates, LocalDateTime createdate, int numberOfParticipants, String description, Date establishmentDate, MusicGenre genre, Album bestAlbum){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = createdate;
        this.numberOfParticipants = numberOfParticipants;
        this.description = description;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public MusicBand(Album album) {
        this.bestAlbum = album;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    /** Метод, возвращает количество людей в мезыкальной группе.
     *
     * @return int - количество людей.
     */
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /** Метод, возвращает ID.
     *
     * @return long - id.
     */
    public long getID(){
        return this.id;
    }

    /** Метод, возвращает количество продаж из Album.
     *
     * @return long - продажи.
     */
    public long getSales(){
        return this.bestAlbum.getSales();
    }

    /** Метод, возвращает имя музыкальной группы.
     *
     * @return String - name.
     */
    public String getName() {
        return name;
    }
    /** Метод, возвращает описание.
     *
     * @return String - описание.
     */
    public String getDescription(){
        return this.description;
    }

    /** Метод, парсит аргументы из String в MusicBand
     *
     * @param id id, обычно генерируется с помощью giveID().
     * @param name Имя объекта. Поле не может быть null, Строка не может быть пустой.
     * @param x Координата X. Значение поля должно быть больше -687.
     * @param y Координата Y. Поле не может быть null.
     * @param numberOfParticipants Количество людей в музыкальной группе. Значение поля должно быть больше 0.
     * @param description Описание музыкальной группы. Поле не может быть null.
     * @param establishmentDate Дата создания музыкальной группы. Формат: yyyy-MM-dd. Поле может быть null.
     * @param genre Жанр(PSYCHEDELIC_ROCK, RAP, POP, POST_ROCK, POST_PUNK). Поле не может быть null.
     * @param albumName Название лучшего альбома.
     * @param tracks Количество треков в лучшем альбоме.
     * @param length Длина лучшего альбома.
     * @param sales Продажи лучшего альбома.
     * @return MusicBand - результат
     */

    /** Переопределенный метод toString
     *
     * @return возвращает объект в виде текста
     */
    @Override
    public String toString() {
        return  "________________________________________________________________" + "\n" +
                "|ID: " + id + "\n" +
                "|Имя: (" + name + ")\n" +
                "|Координаты: " + coordinates + "\n" +
                "|Дата добавления в базу: " + creationDate + " \n" +
                "|Число участников: " + numberOfParticipants + "\n" +
                "|Описание: (" + description + ")\n" +
                "|Дата создания: " + establishmentDate + "\n" +
                "|Жанр: " + genre + "\n" +
                "|Лучший альбом: " + bestAlbum + "\n" +
                "________________________________________________________________";
    }
    @Override
    public int compareTo(MusicBand anotherMusicBand)
    {
        if (this.id.equals(anotherMusicBand.id)) {
            return 0;
        } else if (this.id < anotherMusicBand.id) {
            return -1;
        } else {
            return 1;
        }
    }
}