package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.exception.EntityNotFoundException;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Класс UpdateIdCommand предназначен для обновления элемента коллекции по заданному ID.
 * Наследуется от WithParametersCommand.
 */
public class UpdateIdCommand extends WithParametersCommand  {
    private final LabWorkCreator creator;
    private final Reader reader;

    /**
     * Конструктор UpdateIdCommand.
     *
     * @param receiver объект, управляющий коллекцией.
     * @param reader объект для считывания пользовательского ввода.
     * @param writer объект для вывода информации пользователю.
     * @param flag флаг, определяющий режим работы.
     */
    public UpdateIdCommand(Receiver receiver, Reader reader, Writer writer, int flag) {
        super(receiver, CommandConfiguration.UPDATE_ID_NAME, CommandConfiguration.UPDATE_ID_DESCRIPTION, writer);
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
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        try {
            Long id = Long.parseLong(parameter);
            if (!receiver.existById(id)) {
                writer.println("нет такого id");
            } else {
                receiver.updateById(id, creator.createLabWork());
                writer.println("успешное обновление сущности");
            }
        } catch (NumberFormatException e) {
            writer.println("Формат Id не целое число!");
        } catch (EntityNotFoundException e) {
            writer.println(e.getMessage());
        }
    }
}
