package se.ifmo.create;

import se.ifmo.entity.Coordinates;
import se.ifmo.entity.Difficulty;
import se.ifmo.entity.LabWork;
import se.ifmo.entity.Person;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;

public class LabWorkCreator {
    private final Writer writer;
    private final Reader reader;
    private final FieldValidator fieldValidator = new FieldValidator();
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

    public LabWorkCreator(Reader reader, Writer writer) {
        this.writer = writer;
        this.reader = reader;
    }

    public Person createPerson() {
        return Person.builder()
                .name(createNameAuthor())
                .height(createHeight())
                .passportID(createPassportID())
                .build();
    }

    public LabWork createLabWork() {
        String nameLabWork = createNameWork();

        Coordinates coordinates = Coordinates.builder()
                .x(createCoordinatesX())
                .y(createCoordinatesY())
                .build();

        double minimalPoint = createMinimalPoint();

        Float maximumPoint = createMaximumPoint();

        Difficulty difficulty = createDifficulty();

        Person person = createPerson();

        return LabWork.builder()
                .name(nameLabWork)
                .coordinates(coordinates)
                .minimalPoint(minimalPoint)
                .maximumPoint(maximumPoint)
                .difficulty(difficulty)
                .author(person)
                .build();
    }

    private String createNameWork() {
        writer.print("Введите имя работы, оно не должно быть пустым: ");
        String name = reader.readLine();
        if (fieldValidator.isValidName(name)) {
            return name;
        }
        writer.println(String.format(ERROR_MESSAGE, "имя работы"));
        return createNameWork();
    }

    private int createCoordinatesX() {
        writer.print(String.format("Введите координату X вашей работы," +
                " это должно быть целое число от %d до %d: ", MIN_VALUE_OF_X, MAX_VALUE_OF_X));
        String x = reader.readLine();
        if (fieldValidator.isValidCoordinateX(x)) {
            return Integer.parseInt(x);
        }
        writer.println(String.format(ERROR_MESSAGE, "координата X"));
        return createCoordinatesX();
    }

    private Long createCoordinatesY() {
        writer.print(String.format("Введите координату Y вашей работы, это должно быть целое число" +
                " от %d до %d: ", MIN_VALUE_OF_Y, MAX_VALUE_OF_Y));
        String y = reader.readLine();
        if (fieldValidator.isValidCoordinateY(y)) {
            return Long.parseLong(y);
        }
        writer.println(String.format(ERROR_MESSAGE, "координата Y"));
        return createCoordinatesY();
    }

    private double createMinimalPoint() {
        writer.print(String.format("Введите минимальную оценку вашей работы," +
                " это должно быть число от %s до %s: ", MIN_VALUE_OF_MINIMAL_POINT, MAX_VALUE_OF_MINIMAL_POINT));
        String minimalPoint = reader.readLine();
        if (fieldValidator.isValidMinimalPoint(minimalPoint)) {
            return Double.parseDouble(minimalPoint);
        }
        writer.println(String.format(ERROR_MESSAGE, "минимальная оценка"));
        return createMinimalPoint();
    }

    private Float createMaximumPoint() {
        writer.print(String.format("Введите максимальную оценку вашей работы, это должно быть " +
                "число от %s до %s: ", MIN_VALUE_OF_MAXIMUM_POINT, MAX_VALUE_OF_MAXIMUM_POINT));
        String maximumPoint = reader.readLine();
        if (fieldValidator.isValidMaximumPoint(maximumPoint)) {
            return Float.parseFloat(maximumPoint);
        }
        writer.println(String.format(ERROR_MESSAGE, "максимальная оценка"));
        return createMaximumPoint();
    }

    private Difficulty createDifficulty() {
        writer.print("Введите сложность вашей работы -> : ");
        for (Difficulty o : Difficulty.values()) {
            writer.print(o.toString() + " ");
        }
        String difficulty = reader.readLine();
        if (fieldValidator.isValidDifficulty(difficulty)) {
            return Difficulty.valueOf(difficulty);
        }
        writer.println(String.format(ERROR_MESSAGE, "сложность"));
        return createDifficulty();
    }

    private String createNameAuthor() {
        writer.print("Введите имя автора работы, оно не должно быть пустым: ");
        String name = reader.readLine();
        if (fieldValidator.isValidName(name)) {
            return name;
        }
        writer.println(String.format(ERROR_MESSAGE, "имя автора"));
        return createNameAuthor();
    }

    private Integer createHeight() {
        try {
            writer.print(String.format("Введите рост автора работы, он должен быть больше нуля и быть целым " +
                    "числом до %d или вы можете не задавать его нажав Enter: ", MAX_VALUE_OF_HEIGHT));
            String height = reader.readLine();
            if (fieldValidator.isValidHeight(height)) {
                return Integer.parseInt(height);
            }
            writer.println(String.format(ERROR_MESSAGE, "рост автора работы"));
            return createHeight();
        } catch (Exception e) {
            return null;
        }
    }

    private String createPassportID() {
        writer.print("Введите номер паспорта автора работы, это должен быть набор символов длинной от 9 до 25: ");
        String passportID = reader.readLine();
        if (fieldValidator.isValidPassportID(passportID)) {
            return passportID;
        }
        writer.println(String.format(ERROR_MESSAGE, "ID паспорта"));
        return createPassportID();
    }
}