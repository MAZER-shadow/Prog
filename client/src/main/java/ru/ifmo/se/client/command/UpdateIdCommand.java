package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.client.creator.LabWorkCreator;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestIndex;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;


/**
 * Класс UpdateIdCommand предназначен для обновления элемента коллекции по заданному ID.
 * Наследуется от WithParametersCommand.
 */
public class UpdateIdCommand extends WithParametersCommand  {
    private final LabWorkCreator creator;
    private final Reader reader;

    /**
     * Конструктор UpdateIdCommand.
     * @param reader объект для считывания пользовательского ввода.
     * @param writer объект для вывода информации пользователю.
     * @param flag флаг, определяющий режим работы.
     */
    public UpdateIdCommand(Reader reader, Writer writer, boolean flag) {
        super(CommandConfiguration.UPDATE_ID_NAME, CommandConfiguration.UPDATE_ID_DESCRIPTION, writer);
        this.reader = reader;
        creator = new LabWorkCreator(reader, writer, flag);
    }

    /**
     * Выполняет обновление элемента коллекции по заданному ID.
     * Если ID не найден, выводит сообщение об ошибке.
     *
     * @param parameter строковое представление ID элемента.
     */
    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        try {
            Long id = Long.parseLong(parameter);
            LabWorkDto labWork = creator.createLabWork();
            return RequestIndex.builder()
                    .commandName(name)
                    .index(id)
                    .labWorkDto(labWork)
                    .build();

        } catch (NumberFormatException e) {
            writer.println("Значение id не в том формате");
            return null;
        }
    }

    @Override
    public void handleResponse(Response response) {
        writer.println(response.getMessage());
    }
}
