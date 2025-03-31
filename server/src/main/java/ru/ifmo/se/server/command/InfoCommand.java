package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.receiver.Receiver;

/**
 * Команда для вывода информации о метаданных базы данных.
 * Выводит описание метаданных базы данных, включая ее основные характеристики.
 */
public class InfoCommand extends AbstractCommand  {

    /**
     * Конструктор команды вывода информации о базе данных.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     */
    public InfoCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.INFO_NAME, CommandConfiguration.INFO_DESCRIPTION);
    }
    
    @Override
    public Response execute(Request request) {
        return Response.builder()
                .status(true)
                .message(receiver.getDatabaseDump().getDatabaseMetaData().aboutDatabaseMetaData())
                .build();
    }
}
