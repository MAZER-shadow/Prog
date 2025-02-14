package se.ifmo.preset;

public class CommandConfiguration {
    public static final String ADD_NAME = "add";
    public static final String ADD_DESCRIPTION = "добавить новый элемент в коллекцию";
    public static final String CLEAR_NAME = "clear";
    public static final String CLEAR_DESCRIPTION = "очистить коллекцию";
    public static final String COUNT_GREATER_THAN_AUTHOR_NAME = "countGreaterThanAuthor";
    public static final String  COUNT_GREATER_THAN_AUTHOR_DESCRIPTION = "вывести количество элементов, " +
            "значение поля author которых больше заданного";
    public static final String EXIT_NAME = "exit";
    public static final String EXIT_DESCRIPTION = "завершить программу (без сохранения в файл)";
    public static final String EXECUTE_SCRIPT_NAME = "executeScript";
    public static final String EXECUTE_SCRIPT_DESCRIPTION = "считать и исполнить скрипт из указанного файла. " +
            "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    public static final String GROUP_COUNTING_BY_MINIMAL_POINT_NAME = "groupCountingByMinimalPoint";
    public static final String GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION = "сгруппировать элементы коллекции" +
            " по значению поля minimalPoint, вывести количество элементов в каждой группе";
    public static final String HELP_NAME = "help";
    public static final String HELP_DESCRIPTION = "вывести справку по доступным командам";
    public static final String INFO_NAME = "info";
    public static final String INFO_DESCRIPTION = "вывести в стандартный поток вывода информацию о " +
            "коллекции (тип, дата инициализации, количество элементов и т.д.)";
    public static final String INSERT_AT_INDEX_NAME = "insertAtIndex";
    public static final String INSERT_AT_INDEX_DESCRIPTION = "добавить новый элемент в заданную позицию";
    public static final String MIN_BY_MINIMAL_POINT_NAME = "minByMinimalPoint";
    public static final String MIN_BY_MINIMAL_POINT_DESCRIPTION = "вывести любой объект из коллекции, значение " +
            "поля minimalPoint которого является минимальным";
    public static final String REMOVE_BY_ID_NAME = "removeById";
    public static final String REMOVE_BY_ID_DESCRIPTION = "удалить элемент из коллекции по его id";
    public static final String REMOVE_FIRST_NAME = "removeFirst";
    public static final String REMOVE_FIRST_DESCRIPTION = "удалить первый элемент из коллекции";
    public static final String SAVE_NAME = "save";
    public static final String SAVE_DESCRIPTION = "сохранить коллекцию в файл";
    public static final String SHOW_NAME = "show";
    public static final String SHOW_DESCRIPTION = "вывести в стандартный поток вывода все элементы коллекции " +
            "в строковом представлении";
    public static final String SORT_NAME = "sort";
    public static final String SORT_DESCRIPTION = "отсортировать коллекцию в естественном порядке";
    public static final String UPDATE_ID_NAME = "updateId";
    public static final String UPDATE_ID_DESCRIPTION = "обновить значение элемента коллекции," +
            " id которого равен заданному";
}
