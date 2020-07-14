package lab.Commands;

import lab.Commands.SerializedCommands.Message;

import java.io.IOException;
import java.io.Serializable;

/**
 * Абстрактный класс команд. На его основе создается остальные команды.
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 33L;
    protected abstract void writeInfo();
    protected abstract Message execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException;
}
