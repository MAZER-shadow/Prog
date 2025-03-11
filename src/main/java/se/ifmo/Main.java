package se.ifmo;

/**
 * Класс Main является точкой входа в приложение.
 * Он создает экземпляр класса Starter и запускает его метод run для инициализации и выполнения приложения.
 */
public class Main {
    /**
     * Основной метод, который запускает приложение.
     * Создает экземпляр класса Starter и вызывает его метод run.
     *
     * @param args Аргументы командной строки, передаваемые при запуске приложения.
     */
    public static void main(String[] args) {
        Starter starter = new Starter();
        starter.run();
    }
}
