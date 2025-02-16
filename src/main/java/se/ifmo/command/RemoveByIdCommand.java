package se.ifmo.command;

import se.ifmo.exception.EntityNotFoundException;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для удаления сущности по идентификатору.
 * Проверяет корректность идентификатора и удаляет сущность из коллекции.
 */
public class RemoveByIdCommand extends WithParametersCommand  {

    /**
     * Конструктор команды удаления сущности по идентификатору.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public RemoveByIdCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.REMOVE_BY_ID_NAME, CommandConfiguration.REMOVE_BY_ID_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду удаления сущности по заданному идентификатору.
     * Проверяет, существует ли сущность с таким идентификатором, и если да, удаляет её.
     * Если идентификатор некорректен или сущность не найдена, выводится сообщение об ошибке.
     *
     * @param parameter Параметр команды — идентификатор сущности для удаления.
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        try {
            Long id = Long.parseLong(parameter);
            if (!receiver.existById(id)) {
                writer.println("нет такого id");
            } else {
                receiver.removeById(id);
                writer.println("успешное удаление сущности");
            }
        } catch (NumberFormatException e) {
            writer.println("Формат Id не целое число!");
        } catch (EntityNotFoundException e) {
            writer.println(e.getMessage());
        }
    }
}
