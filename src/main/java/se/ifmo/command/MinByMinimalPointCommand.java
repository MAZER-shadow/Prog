package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Команда для поиска сущности с минимальным значением минимальной точки.
 * Сортирует коллекцию по минимальной точке и выводит информацию о первой сущности.
 */
public class MinByMinimalPointCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды поиска сущности с минимальной минимальной точкой.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public MinByMinimalPointCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.MIN_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.MIN_BY_MINIMAL_POINT_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду поиска сущности с минимальной минимальной точкой.
     * Сортирует все элементы коллекции по минимальной точке и выводит информацию
     * о первой сущности в отсортированном списке.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        List<LabWork> list = new ArrayList<>(receiver.getAll());
        Collections.sort(list, Comparator.comparingDouble(LabWork::getMinimalPoint));
        writer.println(list.get(0).aboutLabWork());
    }
}
