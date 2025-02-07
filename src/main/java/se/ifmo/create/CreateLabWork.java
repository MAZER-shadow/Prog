package se.ifmo.create;

import se.ifmo.entity.Coordinates;
import se.ifmo.entity.Difficulty;
import se.ifmo.entity.LabWork;
import se.ifmo.entity.Person;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;

public class CreateLabWork {
    private final Writer writer;
    private final Reader reader;
    private final ValidField validField = new ValidField();

    public CreateLabWork(Reader reader, Writer writer) {
        this.writer = writer;
        this.reader = reader;
    }

    public LabWork createLabWork() {
        String nameLabWork = createNameWork();

        Coordinates coordinates = Coordinates.builder()
                .x(createCoordinatesX())
                .y(createCoordinatesY())
                .build();

        Double minimalPoint = createMinimalPoint();

        Float maximumPoint = createMaximumPoint();

        Difficulty difficulty = createDifficulty();

        Person person = Person.builder()
                .name(createNameAuthor())
                .height(createHeight())
                .passportID(createPassportID())
                .build();

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
        if (validField.isValidName(name)) {
            return name;
        }
        printErrorInput("имя работы");
        return createNameWork();
    }

    private int createCoordinatesX() {
        writer.print("Введите координату X вашей работы, это должно быть целое число от -436 до 2147483647: ");
        String x = reader.readLine();
        if (validField.isValidCoordinateX(x)) {
            return Integer.parseInt(x);
        }
        printErrorInput("координата X");
        return createCoordinatesX();
    }

    private Long createCoordinatesY() {
        writer.print("Введите координату Y вашей работы, это должно быть целое число" +
                " от -9223372036854775808 до 9223372036854775807: ");
        String y = reader.readLine();
        if (validField.isValidCoordinateY(y)) {
            return Long.parseLong(y);
        }
        printErrorInput("координата Y");
        return createCoordinatesY();
    }

    private double createMinimalPoint() {
        writer.print("Введите минимальную оценку вашей работы, это должно быть число от 0.1*10^-324 до 1.7*10^324: ");
        String minimalPoint = reader.readLine();
        if (validField.isValidMinimalPoint(minimalPoint)) {
            return Double.parseDouble(minimalPoint);
        }
        printErrorInput("минимальная оценка");
        return createMinimalPoint();
    }

    private Float createMaximumPoint() {
        writer.print("Введите максимальную оценку вашей работы, это должно быть число от 0.1*10^-38 до 3.4*10^38: ");
        String maximumPoint = reader.readLine();
        if (validField.isValidMaximumPoint(maximumPoint)) {
            return Float.parseFloat(maximumPoint);
        }
        printErrorInput("максимальная оценка");
        return createMaximumPoint();
    }

    private Difficulty createDifficulty() {
        writer.print("Введите сложность вашей работы -> : ");
        for (Difficulty o : Difficulty.values()) {
            writer.print(o.toString() + " ");
        }
        String difficulty = reader.readLine();
        if (validField.isValidDifficulty(difficulty)) {
            return Difficulty.valueOf(difficulty);
        }
        printErrorInput("сложность");
        return createDifficulty();
    }

    private String createNameAuthor() {
        writer.print("Введите имя автора работы, оно не должно быть пустым: ");
        String name = reader.readLine();
        if (validField.isValidName(name)) {
            return name;
        }
        printErrorInput("имя автора");
        return createNameAuthor();
    }

    private Integer createHeight() {
        writer.print("Введите рост автора работы, он должен быть больше нуля и быть целым " +
                "числом или вы можете не задавать его нажав Enter: ");
        String height = reader.readLine();
        if (validField.firstValidHeight(height)) {
            if (validField.secondValidHeight(height)) {
                return Integer.parseInt(height);
            }
            return null;
        }
        printErrorInput("рост автора работы");
        return createHeight();
    }

    private String createPassportID() {
        writer.print("Введите номер паспорта автора работы, это должен быть набор символов длинной от 9 до 25: ");
        String passportID = reader.readLine();
        if (validField.isValidPassportID(passportID)) {
            return passportID;
        }
        printErrorInput("ID паспорта");
        return createPassportID();
    }

    private void printErrorInput(String data) {
        writer.print(String.format("Некорректно ввели %s, попробуйте ещё раз", data));
        writer.print("\n");
    }
}