package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.client.creator.LabWorkCreator;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLabWork;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
 */
public class AddCommand extends WithoutParametersCommand {
    private final LabWorkCreator creator;

    /**
     * Конструктор команды добавления нового элемента в коллекцию.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода сообщений.
     */
    public AddCommand(Reader reader, Writer writer) {
        super(CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION, writer, true);
        creator = new LabWorkCreator(reader, writer);
    }

    /**
     * Выполняет команду добавления нового элемента в коллекцию.
     * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        LabWorkDto labWork = creator.createLabWork();
        return RequestLabWork.builder()
                .commandName(CommandConfiguration.ADD_NAME)
                .labWorkDto(labWork)
                .build();
    }

    @Override
    public void handleResponse(Response response) {
        if (response instanceof ResponseLabWorkDto responseLabWorkDto) {
            LabWorkDto labWorkDto = responseLabWorkDto.getLabWorkDto();
            writer.println(String.format("Успешное создание сущности LabWork, id = %d", labWorkDto.getId()));
        }
    }
}
