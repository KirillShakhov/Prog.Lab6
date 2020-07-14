package lab.Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import java.util.Scanner;

/**
 * Считыватель примитивного float.
 */
public class PrimitiveFloatReader {
    public static float read(String messageForConsole, float limit, String type) {
        System.out.print(messageForConsole);
        Scanner sc = new Scanner(System.in);
        float result = 0f;
        boolean end = false;
        while (!end) {
            try {
                result = Float.parseFloat(sc.nextLine().trim());
                switch (type) {
                    case ("MIN"):
                        if (result > limit) { end = true; }
                        else { System.out.print("Вы ввели не подходящее значение. " + "Оно должно быть больше " + limit + ". Попробуйте снова: ");}
                        break;
                    case ("MAX"):
                        if (result < limit) { end = true; }
                        else { System.out.print("Вы ввели не подходящее значение. " + "Оно должно быть меньше " + limit + ". Попробуйте снова: ");}
                        break;
                    case ("NULL"):
                        end = true;
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.print("Вы должны ввести число, попробуйте снова: ");
            }
        }

        return result;
    }
}
