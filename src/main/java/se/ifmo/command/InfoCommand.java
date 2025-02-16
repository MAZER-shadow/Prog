package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для вывода информации о метаданных базы данных.
 * Выводит описание метаданных базы данных, включая ее основные характеристики.
 */
public class InfoCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды вывода информации о базе данных.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public InfoCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.INFO_NAME, CommandConfiguration.INFO_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду вывода информации о метаданных базы данных.
     * Выводит описание базы данных, предоставляемое объектом метаданных.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        writer.println(receiver.getDatabaseDump().getDatabaseMetaData().aboutDatabaseMetaData());
    }
}
