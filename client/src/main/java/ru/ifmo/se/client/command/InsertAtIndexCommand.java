package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.client.creator.LabWorkCreator;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestIndex;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;


/**
 * Команда для вставки новой сущности в коллекцию по указанному индексу.
 * Если индекс больше, чем размер коллекции, выводится сообщение об ошибке.
 */
public class InsertAtIndexCommand extends WithParametersCommand  {
    private final LabWorkCreator creator;
    private final boolean flag;

    /**
     * Конструктор команды вставки новой сущности по индексу.
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг для определения дополнительной логики создания сущности.
     */
    public InsertAtIndexCommand(Reader reader, Writer writer, boolean flag) {
        super(CommandConfiguration.INSERT_AT_INDEX_NAME,
                CommandConfiguration.INSERT_AT_INDEX_DESCRIPTION, writer);
        creator = new LabWorkCreator(reader, writer, flag);
        this.flag = flag;
    }

    /**
     * Выполняет команду вставки новой сущности в коллекцию по указанному индексу.
     * Если индекс больше, чем размер коллекции, выводится сообщение об ошибке.
     * Вставленная сущность получает уникальный id и текущую дату создания.
     *
     * @param parameter Параметр команды — индекс, на который нужно вставить сущность.
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
        ResponseLabWorkDto responseLabWorkDto = (ResponseLabWorkDto) response;
        writer.println(String.format("Успешно вставлена сущность с id = %d", responseLabWorkDto.getLabWorkDto().getId()));
    }
}
