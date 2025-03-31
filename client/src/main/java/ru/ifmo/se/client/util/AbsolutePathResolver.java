package ru.ifmo.se.client.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс AbsolutePathResolver предоставляет функциональность для преобразования относительных путей в абсолютные.
 * Он обрабатывает пути, начинающиеся с символов "~", "." или "/", и преобразует их в абсолютные пути.
 */
public class AbsolutePathResolver {

    /**
     * Преобразует входящий путь в абсолютный путь. Если путь
     * начинается с "~", он заменяется на домашнюю директорию пользователя.
     * Если путь начинается с "." или "/", он преобразуется в
     * абсолютный путь с использованием текущей рабочей директории.
     *
     * @param incomingPath Входящий путь, который необходимо преобразовать.
     * @return Абсолютный путь, соответствующий входящему пути.
     */
    public String resolvePath(String incomingPath) {
        if (incomingPath != null && (incomingPath.startsWith(".") ||
                incomingPath.startsWith("~") || incomingPath.startsWith("/"))) {
            if (incomingPath.startsWith("~")) {
                String homeDir = System.getProperty("user.home");
                incomingPath = incomingPath.replaceFirst("~", homeDir);
            }
            Path absolutePath = Paths.get(incomingPath).toAbsolutePath().normalize();
            incomingPath = absolutePath.toString();
        }
        return incomingPath;
    }
}
