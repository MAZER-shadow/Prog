package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.util.Map;

/**
 * Команда для вывода справки по доступным командам.
 * Выводит список всех команд и их описания.
 */
public class HelpCommand extends WithoutParametersCommand  {
    private StringBuilder resultCommand;

    /**
     * Конструктор команды справки.
     * Инициализирует список доступных команд и их описания.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     * @param descriptionMap Карта, содержащая описание каждой команды.
     */
    public HelpCommand(Receiver receiver, Writer writer, Map<String, String> descriptionMap) {
        super(receiver, CommandConfiguration.HELP_NAME, CommandConfiguration.HELP_DESCRIPTION, writer);
        doResultCommand(descriptionMap);
    }

    /**
     * Выполняет команду вывода справки по доступным командам.
     * Выводит список команд с их описаниями.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        writer.println(resultCommand.toString());
    }

    /**
     * Формирует строку с описаниями всех команд.
     * Для каждой команды из переданной карты добавляется строка с названием команды и ее описанием.
     *
     * @param descriptionMap Карта с описаниями команд.
     */
    private void doResultCommand(Map<String, String> descriptionMap) {
        resultCommand = new StringBuilder();
        descriptionMap.put(name, description);
        for (Map.Entry<String, String> stringStringEntry : descriptionMap.entrySet()) {
            resultCommand.append(stringStringEntry.getKey()).append(": ")
                    .append(stringStringEntry.getValue()).append("\n");
        }
    }
}
