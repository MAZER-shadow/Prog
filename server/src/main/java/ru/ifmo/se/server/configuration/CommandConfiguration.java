package ru.ifmo.se.server.configuration;

/**
 * Класс CommandConfiguration содержит конфигурационные данные для команд приложения.
 * В нем определены имена и описания всех доступных команд, которые могут быть использованы в приложении.
 */
public class CommandConfiguration {
    /**
     * Имя команды добавления нового элемента в коллекцию.
     */
    public static final String ADD_NAME = "add";

    /**
     * Описание команды добавления нового элемента в коллекцию.
     */
    public static final String ADD_DESCRIPTION = "добавить новый элемент в коллекцию";

    public static final String AUTHORIZATION_NAME = "authorization";

    public static final String AUTHORIZATION_DESCRIPTION = "авторизоваться";

    public static final String REGISTRATION_NAME = "registration";

    public static final String REGISTRATION_DESCRIPTION = "зарегистрироваться";
    /**
     * Имя команды очистки коллекции.
     */
    public static final String CLEAR_NAME = "clear";

    /**
     * Описание команды очистки коллекции.
     */
    public static final String CLEAR_DESCRIPTION = "очистить коллекцию";

    /**
     * Имя команды подсчета элементов, значение поля author которых больше заданного.
     */
    public static final String COUNT_GREATER_THAN_AUTHOR_NAME = "countGreaterThanAuthor";

    /**
     * Описание команды подсчета элементов, значение поля author которых больше заданного.
     */
    public static final String COUNT_GREATER_THAN_AUTHOR_DESCRIPTION = "вывести количество элементов, " +
            "значение поля author которых больше заданного";

    /**
     * Имя команды группировки элементов коллекции по значению поля minimalPoint.
     */
    public static final String GROUP_COUNTING_BY_MINIMAL_POINT_NAME = "groupCountingByMinimalPoint";

    /**
     * Описание команды группировки элементов коллекции по значению поля minimalPoint.
     */
    public static final String GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION = "сгруппировать элементы коллекции" +
            " по значению поля minimalPoint, вывести количество элементов в каждой группе";

    /**
     * Имя команды вывода справки по доступным командам.
     */
    public static final String HELP_NAME = "help";

    /**
     * Описание команды вывода справки по доступным командам.
     */
    public static final String HELP_DESCRIPTION = "вывести справку по доступным командам";

    /**
     * Имя команды добавления нового элемента в заданную позицию.
     */
    public static final String INSERT_AT_INDEX_NAME = "insertAtIndex";

    /**
     * Описание команды добавления нового элемента в заданную позицию.
     */
    public static final String INSERT_AT_INDEX_DESCRIPTION = "добавить новый элемент в заданную позицию";

    /**
     * Имя команды вывода объекта с минимальным значением поля minimalPoint.
     */
    public static final String MIN_BY_MINIMAL_POINT_NAME = "minByMinimalPoint";

    /**
     * Описание команды вывода объекта с минимальным значением поля minimalPoint.
     */
    public static final String MIN_BY_MINIMAL_POINT_DESCRIPTION = "вывести любой объект из коллекции, значение " +
            "поля minimalPoint которого является минимальным";

    /**
     * Имя команды удаления элемента из коллекции по его id.
     */
    public static final String REMOVE_BY_ID_NAME = "removeById";

    /**
     * Описание команды удаления элемента из коллекции по его id.
     */
    public static final String REMOVE_BY_ID_DESCRIPTION = "удалить элемент из коллекции по его id";
    /**
     * Имя команды удаления первого элемента из коллекции.
     */
    public static final String REMOVE_FIRST_NAME = "removeFirst";

    /**
     * Описание команды удаления первого элемента из коллекции.
     */
    public static final String REMOVE_FIRST_DESCRIPTION = "удалить первый элемент из коллекции";

    /**
     * Имя команды вывода всех элементов коллекции в строковом представлении.
     */
    public static final String SHOW_NAME = "show";

    /**
     * Описание команды вывода всех элементов коллекции в строковом представлении.
     */
    public static final String SHOW_DESCRIPTION = "вывести в стандартный поток вывода все элементы коллекции " +
            "в строковом представлении";

    /**
     * Имя команды сортировки коллекции в естественном порядке.
     */
    public static final String SORT_NAME = "sort";

    /**
     * Описание команды сортировки коллекции в естественном порядке.
     */
    public static final String SORT_DESCRIPTION = "отсортировать коллекцию в естественном порядке";

    /**
     * Имя команды обновления значения элемента коллекции по его id.
     */
    public static final String UPDATE_ID_NAME = "updateId";

    /**
     * Описание команды обновления значения элемента коллекции по его id.
     */
    public static final String UPDATE_ID_DESCRIPTION = "обновить значение элемента коллекции," +
            " id которого равен заданному";
}
