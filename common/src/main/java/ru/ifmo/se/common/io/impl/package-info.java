/**
 * Пакет impl содержит конкретные реализации интерфейсов ввода-вывода, определенных в пакете {@link se.ifmo.io}.
 * Основные классы:
 * <ul>
 *   <li>{@link ru.ifmo.se.server.io.impl.JsonReaderImpl} -
 *   Реализация интерфейса {@link ru.ifmo.se.server.io.JsonReader} для чтения данных в формате JSON.</li>
 *   <li>{@link ru.ifmo.se.server.io.impl.JsonWriterImpl} -
 *   Реализация интерфейса {@link ru.ifmo.se.server.io.JsonWriter} для записи данных в формате JSON.</li>
 *   <li>{@link ru.ifmo.se.server.io.impl.ReaderImpl} - Базовая реализация интерфейса {@link ru.ifmo.se.server.io.Reader}.</li>
 *   <li>{@link ru.ifmo.se.server.io.impl.WriterImpl} - Базовая реализация интерфейса {@link ru.ifmo.se.server.io.Writer}.</li>
 * </ul>
 *
 * Эти классы предоставляют конкретные способы обработки ввода-вывода данных для приложения.
 */
package ru.ifmo.se.common.io.impl;
