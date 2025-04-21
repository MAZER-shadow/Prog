package ru.ifmo.se.client.command;


import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.client.creator.LabWorkCreator;
import ru.ifmo.se.common.dto.model.PersonDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestPersonDto;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для подсчёта количества элементов в коллекции, чьи авторы имеют большую
 * лексикографическую значимость, чем переданный объект Person.
 */
public class CountGreaterThanAuthorCommand extends WithoutParametersCommand  {
    private final Reader reader;
    private final boolean flag;

    /**
     * Конструктор команды подсчёта элементов с автором, лексикографически большим, чем переданный.
     * @param reader Читатель для создания объектов.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг, используемый для создания данных.
     */
    public CountGreaterThanAuthorCommand(Reader reader, Writer writer, boolean flag) {
        super(CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_NAME,
                CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_DESCRIPTION, writer);
        this.reader = reader;
        this.flag = flag;
    }

    /**
     * Выполняет команду подсчёта количества элементов, у которых автор лексикографически
     * больше переданного объекта Person.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        LabWorkCreator labWorkCreator = new LabWorkCreator(reader, writer , flag);
        PersonDto person = labWorkCreator.createPerson();
        return RequestPersonDto
                .builder()
                .commandName(name)
                .person(person)
                .build();
    }

    @Override
    public void handleResponse(Response response) {
        writer.println(response.getMessage());
    }
}
