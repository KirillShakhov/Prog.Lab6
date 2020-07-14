package lab.Commands.Utils.Readers.EnumReaders;

import lab.BasicClasses.MusicGenre;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Считыватель цвета.
 */
public class GenreReader {
    public static boolean checkExist(String toContains) {
        return Arrays.stream(MusicGenre.values()).anyMatch((color) -> color.name().equals(toContains));
    }

    public static MusicGenre read(String messageForConsole, boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print(messageForConsole + " Выберите цвет из представленных(" + Arrays.asList(MusicGenre.values()) + "): ");
        String toContains = in.nextLine().trim().toUpperCase();

        if ((!checkExist(toContains)) && !canBeNull && !toContains.equals("") || !canBeNull && toContains.equals("") || canBeNull && !toContains.equals("")) {
            while (!checkExist(toContains)) {
                System.out.print("Вы ввели несуществующее значение из представленных. Попробуйте снова: ");
                toContains = in.nextLine().trim().toUpperCase();
                checkExist(toContains);
            }
        }

        if (canBeNull && toContains.equals("")) { return null; }

        return Enum.valueOf(MusicGenre.class, toContains);
    }
}
