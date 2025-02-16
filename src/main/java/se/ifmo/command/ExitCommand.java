package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для завершения работы программы.
 * При выполнении этой команды программа завершает свое выполнение.
 */
public class ExitCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды завершения работы программы.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public ExitCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.EXIT_NAME, CommandConfiguration.EXIT_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду завершения работы программы.
     * После выполнения команды программа завершает выполнение с кодом 0.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        writer.println("Завершение программы");
        System.exit(0);
    }
}
