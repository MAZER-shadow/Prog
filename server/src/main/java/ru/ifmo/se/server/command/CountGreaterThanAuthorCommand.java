package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestPersonDto;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.creator.LabWorkFieldValidator;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.exception.PersonDtoException;
import ru.ifmo.se.server.mapper.PersonMapper;
import ru.ifmo.se.server.receiver.Receiver;

/**
 * Команда для подсчёта количества элементов в коллекции, чьи авторы имеют большую
 * лексикографическую значимость, чем переданный объект Person.
 */
public class CountGreaterThanAuthorCommand extends AbstractCommand {

    /**
     * Конструктор команды подсчёта элементов с автором, лексикографически большим, чем переданный.
     *
     */
    public CountGreaterThanAuthorCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_NAME,
                CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_DESCRIPTION);
    }

    @Override
    public Response execute(Request request) {
        RequestPersonDto requestPersonDto = (RequestPersonDto) request;
        LabWorkFieldValidator validator = new LabWorkFieldValidator();
        try {
            validator.validatePerson(requestPersonDto.getPerson());
        } catch (PersonDtoException e) {
            return Response.builder()
                    .status(false)
                    .message(e.getMessage())
                    .build();
        }

        long count = receiver.getAll()
                .stream()
                .map(LabWork::getAuthor)
                .filter(element -> element.compareTo(PersonMapper.INSTANCE.toEntity(requestPersonDto.getPerson())) > 0)
                .count();
        return Response.builder()
                .status(true)
                .message(String.format("Результат CountGreaterThanAuthorCommand: %s", count))
                .build();
    }
}
