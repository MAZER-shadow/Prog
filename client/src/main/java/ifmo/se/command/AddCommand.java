package ifmo.se.command;

import ifmo.se.configuration.CommandConfiguration;
import ifmo.se.creator.LabWorkCreator;
import ifmo.se.entity.LabWorkDto;
import ifmo.se.exception.ParametrBrokeException;
import ifmo.se.io.Reader;
import ifmo.se.io.Writer;
import ifmo.se.request.AbstractRequest;
import ifmo.se.request.DtoRequest;
import ifmo.se.response.AbstractResponse;
import ifmo.se.response.LabWorkResponse;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
 */
public class AddCommand extends WithoutParametersCommand  {
    private final LabWorkCreator creator;

    /**
     * Конструктор команды добавления нового элемента в коллекцию.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг, используемый для настройки создания сущности.
     */
    public AddCommand(Reader reader, Writer writer, boolean flag) {
        super(CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION, writer);
        creator = new LabWorkCreator(reader, writer, flag);
    }

    /**
     * Выполняет команду добавления нового элемента в коллекцию.
     * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public AbstractRequest execute(String parameter) {
        if (!checkParameters(parameter, writer)) {
            throw new ParametrBrokeException(String.format("%s не нуждается в параметре", getName()));
        }
        LabWorkDto labWorkDto = creator.createLabWorkDto();
        return new DtoRequest(CommandConfiguration.ADD_NAME, labWorkDto);
    }

    @Override
    public void handleResponse(AbstractResponse response) {
        if (response.isStatus()) {
            LabWorkResponse labWorkResponse = (LabWorkResponse) response;
            writer.println(String.format("Успешное создание сущности LabWork, id = %d", labWorkResponse.getWork().getId()));
        } else {
            writer.println(response.getMessage());
        }
    }
}
