/**
 * Пакет exception содержит пользовательские исключения, используемые в приложении.
 * Основные классы:
 * <ul>
 *   <li>{@link ru.ifmo.se.server.exception.CommandNotFoundException} - Исключение, выбрасываемое при отсутствии команды.</li>
 *   <li>{@link ru.ifmo.se.server.exception.DumpDataBaseValidationException} - Исключение,
 *   выбрасываемое при ошибке валидации дампа базы данных.</li>
 *   <li>{@link ru.ifmo.se.server.exception.EntityNotFoundException} - Исключение, выбрасываемое при отсутствии сущности.</li>
 *   <li>{@link ru.ifmo.se.server.exception.FileReadException} - Исключение, выбрасываемое при ошибке чтения файла.</li>
 *   <li>{@link ru.ifmo.se.server.exception.FileWriteException} - Исключение, выбрасываемое при ошибке записи в файл.</li>
 *   <li>{@link ru.ifmo.se.server.exception.IORuntimeException} - Runtime исключение, обертывающее IOException.</li>
 *   <li>{@link ru.ifmo.se.server.exception.NonNullException} - Исключение,
 *   выбрасываемое при попытке присвоить полю null значение, когда это не допускается.</li>
 *   <li>{@link ru.ifmo.se.server.exception.NonNullScriptException} - Исключение,
 *   выбрасываемое при попытке выполнить скрипт, а там null-значение.</li>
 * </ul>
 *
 * Эти исключения позволяют обрабатывать различные ошибки, возникающие в процессе работы приложения.
 */
package ru.ifmo.se.server.exception;
