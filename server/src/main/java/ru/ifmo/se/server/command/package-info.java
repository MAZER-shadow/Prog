/**
 * Пакет command содержит классы для управления командами в приложении.
 * Основные классы:
 * <ul>
 *   <li>{@link ru.ifmo.se.server.command.AbstractCommand} - абстрактный класс, представляющий базовую команду.</li>
 *   <li>{@link ru.ifmo.se.server.command.AddCommand} - команда для добавления нового элемента в коллекцию.</li>
 *   <li>{@link ru.ifmo.se.server.command.ClearCommand} - команда для очистки коллекции.</li>
 *   <li>{@link ru.ifmo.se.server.command.CommandManager} - менеджер команд, управляющий регистрацией и выполнением команд.</li>
 *   <li>{@link ru.ifmo.se.server.command.CountGreaterThanAuthorCommand} - команда для подсчета элементов,
 *   значение поля author которых больше заданного.</li>
 *   <li>{@link ru.ifmo.se.server.command.ExecuteScriptCommand} - команда для выполнения скрипта из указанного файла.</li>
 *   <li>{@link ru.ifmo.se.server.command.special.ExitCommand} - команда для завершения программы.</li>
 *   <li>{@link ru.ifmo.se.server.command.GroupCountingByMinimalPointCommand} - команда для группировки
 *   элементов коллекции по значению поля minimalPoint.</li>
 *   <li>{@link ru.ifmo.se.server.command.HelpCommand} - команда для вывода справки по доступным командам.</li>
 *   <li>{@link ru.ifmo.se.server.command.InfoCommand} - команда для вывода информации о коллекции.</li>
 *   <li>{@link ru.ifmo.se.server.command.InsertAtIndexCommand} - команда для добавления нового элемента в заданную позицию.</li>
 *   <li>{@link ru.ifmo.se.server.command.MinByMinimalPointCommand} - команда для вывода объекта с
 *   минимальным значением поля minimalPoint.</li>
 *   <li>{@link ru.ifmo.se.server.command.RemoveByIdCommand} - команда для удаления элемента из коллекции по его id.</li>
 *   <li>{@link ru.ifmo.se.server.command.RemoveFirstCommand} - команда для удаления первого элемента из коллекции.</li>
 *   <li>{@link ru.ifmo.se.server.command.SaveCommand} - команда для сохранения коллекции в файл.</li>
 *   <li>{@link ru.ifmo.se.server.command.ShowCommand} - команда для вывода всех элементов коллекции.</li>
 *   <li>{@link ru.ifmo.se.server.command.SortCommand} - команда для сортировки коллекции в естественном порядке.</li>
 *   <li>{@link ru.ifmo.se.server.command.UpdateIdCommand} - команда для обновления значения элемента коллекции по его id.</li>
 *   <li>{@link ru.ifmo.se.server.command.WithoutParametersCommand} - команда, не требующая параметров для выполнения.</li>
 * </ul>
 */
package ru.ifmo.se.server.command;