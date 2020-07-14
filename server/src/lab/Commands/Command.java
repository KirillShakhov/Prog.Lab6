package lab.Commands;

import java.io.IOException;
import java.io.Serializable;

/**
 * Абстрактный класс команд. На его основе создается остальные команды.
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 33L;
    public abstract String execute(Object argObject) throws IOException;
}