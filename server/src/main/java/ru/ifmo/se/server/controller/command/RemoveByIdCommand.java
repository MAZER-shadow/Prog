package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestId;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.EntityNotFoundException;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.service.LabWorkService;

/**
 * Команда для удаления сущности по идентификатору.
 * Проверяет корректность идентификатора и удаляет сущность из коллекции.
 */
public class RemoveByIdCommand extends AbstractCommand {

    /**
     * Конструктор команды удаления сущности по идентификатору.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public RemoveByIdCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.REMOVE_BY_ID_NAME, CommandConfiguration.REMOVE_BY_ID_DESCRIPTION);
    }

    /**
     * Выполняет команду удаления сущности по заданному идентификатору.
     * Проверяет, существует ли сущность с таким идентификатором, и если да, удаляет её.
     * Если идентификатор некорректен или сущность не найдена, выводится сообщение об ошибке.
     */
    @Override
    public Response execute(Request request) {
        if (request instanceof RequestId) {
            try {
                RequestId requestId = (RequestId) request;
                Long id = Long.parseLong(String.valueOf(requestId.getId()));
                if (!labWorkService.existById(id)) {
                    return Response.builder()
                            .status(false)
                            .message("Нет такого id")
                            .build();
                } else {
                    labWorkService.removeById(id);
                    return Response.builder()
                            .status(true)
                            .message("успешное удаление сущности")
                            .build();
                }
            } catch (NumberFormatException e) {
                return Response.builder()
                        .status(false)
                        .message("Формат Id не целое число!")
                        .build();
            } catch (EntityNotFoundException e) {
                return Response.builder()
                        .status(false)
                        .message(e.getMessage())
                        .build();
            }
        }
        return Response.builder().status(false).build();
    }
}
