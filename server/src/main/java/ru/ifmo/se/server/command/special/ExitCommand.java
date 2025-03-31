package ru.ifmo.se.server.command.special;

import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.receiver.Receiver;

/**
 * Команда для завершения работы программы.
 * При выполнении этой команды программа завершает свое выполнение.
 */
public class ExitCommand extends AbstractSpecialCommand {
    private String path;
    /**
     * Конструктор команды завершения работы программы.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public ExitCommand(Receiver receiver, Writer writer, String path) {
        super(receiver, CommandConfiguration.EXIT_NAME, CommandConfiguration.EXIT_DESCRIPTION, writer);
        this.path = path;
    }

    /**
     * Выполняет команду завершения работы программы.
     * После выполнения команды программа завершает выполнение с кодом 0.
     *

     */
    @Override
    public void execute(String parameter) {
        if (!checkParameters(parameter)) {
            writer.println(String.format("%s не нуждается в параметре", getName()));
            return;
        }
        SaveCommand save = new SaveCommand(receiver, writer, path);
        save.execute("");
        writer.println("Завершение программы");
        System.exit(0);
    }
}
