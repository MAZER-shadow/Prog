package ifmo.se.command;

import ifmo.se.configuration.CommandConfiguration;
import ifmo.se.creator.LabWorkFieldValidator;
import ifmo.se.entity.LabWork;
import ifmo.se.entity.LabWorkDto;
import ifmo.se.exception.DumpDataBaseValidationException;
import ifmo.se.exception.ParametrBrokeException;
import ifmo.se.io.Reader;
import ifmo.se.io.Writer;
import ifmo.se.receiver.Receiver;
import ifmo.se.request.AbstractRequest;
import ifmo.se.request.DtoRequest;
import ifmo.se.response.AbstractResponse;
import ifmo.se.response.LabWorkResponse;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
 */
public class AddCommand extends WithoutParametersCommand {

    /**
     * Конструктор команды добавления нового элемента в коллекцию.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг, используемый для настройки создания сущности.
     */
    public AddCommand(Receiver receiver, Reader reader, Writer writer, boolean flag) {
        super(receiver, CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду добавления нового элемента в коллекцию.
     * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public AbstractResponse execute(String parameter, AbstractRequest request) {
        try {
            if (!checkParameters(parameter, writer)) {
                throw new ParametrBrokeException(String.format("%s не нуждается в параметре", getName()));
            }
            DtoRequest dtoRequest = (DtoRequest) request;
            LabWorkDto labWorkDto = dtoRequest.getLabWorkDto();
            LabWorkFieldValidator validator = new LabWorkFieldValidator();
            validator.validateLabWorkDto(labWorkDto);
            LabWork labWork = LabWork.builder()
                    .name(labWorkDto.getName())
                    .coordinates(labWorkDto.getCoordinates())
                    .minimalPoint(labWorkDto.getMinimalPoint())
                    .maximumPoint(labWorkDto.getMaximumPoint())
                    .difficulty(labWorkDto.getDifficulty())
                    .author(labWorkDto.getAuthor())
                    .build();
            LabWork labWorkLast = receiver.add(labWork);
            return new LabWorkResponse(true, "", labWorkLast);
        } catch (DumpDataBaseValidationException e) {
            return new AbstractResponse(false, e.getMessage());
        }
    }
}
