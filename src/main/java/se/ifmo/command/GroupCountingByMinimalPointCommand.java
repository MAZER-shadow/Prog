package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда для группировки элементов коллекции по минимальной оценке и подсчета их количества.
 * Выводит количество элементов для каждой уникальной минимальной точки.
 */
public class GroupCountingByMinimalPointCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды группировки и подсчета элементов по минимальной оценке.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public GroupCountingByMinimalPointCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду группировки элементов по минимальной оценке и выводит количество
     * элементов для каждой уникальной минимальной оценке.
     * Если коллекция пуста, выводится соответствующее сообщение.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        if (receiver.getAll().isEmpty()) {
            writer.println("В коллекции нет элементов");
        } else {
            Map<Double, Long> groups = receiver.getAll().stream()
                    .collect(Collectors.groupingBy(
                            LabWork::getMinimalPoint,
                            Collectors.counting()
                    ));
            groups.forEach((minimalPoint, count) ->
                    writer.println("minimalPoint: " + minimalPoint + " -> Количество: " + count)
            );
        }
    }
}
