package ru.ifmo.se.client.creator;


import ru.ifmo.se.client.exception.NonNullScriptException;
import ru.ifmo.se.common.dto.model.CoordinatesDto;
import ru.ifmo.se.common.dto.model.DifficultyDto;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.model.PersonDto;
import ru.ifmo.se.common.exception.NonNullException;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;

/**
 * Класс LabWorkCreator предназначен для создания объектов LabWork и Person
 * с валидированными данными, вводимыми пользователем.
 * Обеспечивает корректность данных и повторный ввод при ошибках.
 */
public class LabWorkCreator {
    private final boolean flag;
    private final Writer writer;
    private final Reader reader;
    private final LabWorkFieldValidator labWorkFieldValidator = new LabWorkFieldValidator();
    private final static int MAX_VALUE_OF_X = 2147483647;
    private final static int MIN_VALUE_OF_X = -436;
    private final static long MIN_VALUE_OF_Y = -9223372036854775808L;
    private final static long MAX_VALUE_OF_Y = 9223372036854775807L;
    private final static String MIN_VALUE_OF_MINIMAL_POINT = "0.1*10^-324";
    private final static String MAX_VALUE_OF_MINIMAL_POINT = "1.7*10^324";
    private final static String MIN_VALUE_OF_MAXIMUM_POINT = "0.1*10^-38";
    private final static String MAX_VALUE_OF_MAXIMUM_POINT = "3.4*10^38";
    private final static int MAX_VALUE_OF_HEIGHT = 2147483647;
    private final static String ERROR_MESSAGE = "Некорректно ввели %s, попробуйте ещё раз";

    /**
     * Конструктор класса LabWorkCreator.
     *
     * @param reader объект для чтения пользовательского ввода.
     * @param writer объект для вывода информации пользователю.
     * @param flag   флаг режима работы (например, режим скрипта или консольный ввод).
     */
    public LabWorkCreator(Reader reader, Writer writer, boolean flag) {
        this.flag = flag;
        this.writer = writer;
        this.reader = reader;
    }

    /**
     * Создаёт объект Person с валидными значениями.
     *
     * @return объект Person.
     */
    public PersonDto createPerson() {
        return PersonDto.builder()
                .name(createNameAuthor())
                .height(createHeight())
                .passportID(createPassportID())
                .build();
    }

    /**
     * Создаёт объект LabWork с валидными значениями.
     *
     * @return объект LabWork.
     */
    public LabWorkDto createLabWork() {
        String nameLabWork = createNameWork();

        CoordinatesDto coordinates = CoordinatesDto.builder()
                .x(createCoordinatesX())
                .y(createCoordinatesY())
                .build();

        double minimalPoint = createMinimalPoint();

        Float maximumPoint = createMaximumPoint();

        DifficultyDto difficulty = createDifficulty();

        PersonDto person = createPerson();

        return LabWorkDto.builder()
                .name(nameLabWork)
                .coordinates(coordinates)
                .minimalPoint(minimalPoint)
                .maximumPoint(maximumPoint)
                .difficulty(difficulty)
                .author(person)
                .build();
    }

    /**
     * Запрашивает у пользователя и валидирует название работы.
     *
     * @return строка с названием работы.
     */
    private String createNameWork() {
        if (flag) {
            writer.print("Введите имя работы, оно не должно быть пустым: ");
        }
        try {
            String name = reader.readLine();
            if (labWorkFieldValidator.isValidName(name)) {
                return name;
            }
            writer.println(String.format(ERROR_MESSAGE, "имя работы"));
            return createNameWork();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createNameWork();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует координату X.
     *
     * @return целочисленное значение координаты X.
     */
    private int createCoordinatesX() {
        if (flag) {
            writer.print(String.format("Введите координату X вашей работы," +
                    " это должно быть целое число от %d до %d: ", MIN_VALUE_OF_X, MAX_VALUE_OF_X));
        }
        try {
            String x = reader.readLine();
            if (labWorkFieldValidator.isValidCoordinateX(x)) {
                return Integer.parseInt(x);
            }
            writer.println(String.format(ERROR_MESSAGE, "координата X"));
            return createCoordinatesX();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createCoordinatesX();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует координату Y.
     *
     * @return значение координаты Y типа Long.
     */
    private Long createCoordinatesY() {
        if (flag) {
            writer.print(String.format("Введите координату Y вашей работы, это должно быть целое число" +
                    " от %d до %d: ", MIN_VALUE_OF_Y, MAX_VALUE_OF_Y));
        }
        try {
            String y = reader.readLine();
            if (labWorkFieldValidator.isValidCoordinateY(y)) {
                return Long.parseLong(y);
            }
            writer.println(String.format(ERROR_MESSAGE, "координата Y"));
            return createCoordinatesY();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createCoordinatesY();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует минимальную оценку работы.
     *
     * @return значение минимальной оценки типа double.
     */
    private double createMinimalPoint() {
        if (flag) {
            writer.print(String.format("Введите минимальную оценку вашей работы," +
                    " это должно быть число от %s до %s: ", MIN_VALUE_OF_MINIMAL_POINT, MAX_VALUE_OF_MINIMAL_POINT));
        }
        try {
            String minimalPoint = reader.readLine();
            if (labWorkFieldValidator.isValidMinimalPoint(minimalPoint)) {
                return Double.parseDouble(minimalPoint);
            }
            writer.println(String.format(ERROR_MESSAGE, "минимальная оценка"));
            return createMinimalPoint();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createMinimalPoint();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует максимальную оценку работы.
     *
     * @return значение максимальной оценки типа Float.
     */
    private Float createMaximumPoint() {
        if (flag) {
            writer.print(String.format("Введите максимальную оценку вашей работы, это должно быть " +
                    "число от %s до %s: ", MIN_VALUE_OF_MAXIMUM_POINT, MAX_VALUE_OF_MAXIMUM_POINT));
        }
        try {
            String maximumPoint = reader.readLine();
            if (labWorkFieldValidator.isValidMaximumPoint(maximumPoint)) {
                return Float.parseFloat(maximumPoint);
            }
            writer.println(String.format(ERROR_MESSAGE, "максимальная оценка"));
            return createMaximumPoint();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createMaximumPoint();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует сложность работы.
     *
     * @return объект enum Difficulty или null, если не задано.
     */
    private DifficultyDto createDifficulty() {
        if (flag) {
            writer.print("Введите сложность вашей работы, или вы можете не задавать её нажав Enter: -> : ");
            for (DifficultyDto o : DifficultyDto.values()) {
                writer.print(o.toString() + " ");
            }
        }
        try {
            String difficulty = reader.readLine();
            if (labWorkFieldValidator.isValidDifficulty(difficulty)) {
                return DifficultyDto.valueOf(difficulty);
            }
            writer.println(String.format(ERROR_MESSAGE, "сложность"));
            return createDifficulty();
        } catch (IllegalArgumentException e) {
            return null;
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createDifficulty();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует имя автора.
     *
     * @return строка с именем автора.
     */
    private String createNameAuthor() {
        if (flag) {
            writer.print("Введите имя автора работы, оно не должно быть пустым: ");
        }
        try {
            String name = reader.readLine();
            if (labWorkFieldValidator.isValidName(name)) {
                return name;
            }
            writer.println(String.format(ERROR_MESSAGE, "имя автора"));
            return createNameAuthor();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createNameAuthor();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует рост автора.
     *
     * @return значение роста типа Integer или null, если не задано.
     */
    private Integer createHeight() {
        if (flag) {
            writer.print(String.format("Введите рост автора работы, он должен быть больше нуля и быть целым " +
                    "числом до %d или вы можете не задавать его нажав Enter: ", MAX_VALUE_OF_HEIGHT));
        }
        try {
            String height = reader.readLine();
            if (labWorkFieldValidator.isValidHeight(height)) {
                return Integer.parseInt(height);
            }
            writer.println(String.format(ERROR_MESSAGE, "рост автора работы"));
            return createHeight();
        } catch (NumberFormatException e) {
            return null;
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createHeight();
        }
    }

    /**
     * Запрашивает у пользователя и валидирует ID паспорта автора.
     *
     * @return строка с номером паспорта.
     */
    private String createPassportID() {
        if (flag) {
            writer.print("Введите номер паспорта автора работы, это должен быть набор символов длинной от 9 до 25: ");
        }
        try {
            String passportID = reader.readLine();
            if (labWorkFieldValidator.isValidPassportID(passportID)) {
                return passportID;
            }
            writer.println(String.format(ERROR_MESSAGE, "ID паспорта"));
            return createPassportID();
        } catch (NonNullException e) {
            if (flag) {
                writer.println(e.getMessage());
            }
            if (!flag) {
                writer.println(e.getMessage());
                throw new NonNullScriptException(e.getMessage());
            }
            return createPassportID();
        }
    }
}
