/**
 * Пакет command содержит классы для управления командами в приложении.
 * Основные классы:
 * <ul>
 *   <li>{@link se.ifmo.command.AbstractCommand} - абстрактный класс, представляющий базовую команду.</li>
 *   <li>{@link se.ifmo.command.AddCommand} - команда для добавления нового элемента в коллекцию.</li>
 *   <li>{@link se.ifmo.command.ClearCommand} - команда для очистки коллекции.</li>
 *   <li>{@link se.ifmo.command.CommandManager} - менеджер команд, управляющий регистрацией и выполнением команд.</li>
 *   <li>{@link se.ifmo.command.CountGreaterThanAuthorCommand} - команда для подсчета элементов,
 *   значение поля author которых больше заданного.</li>
 *   <li>{@link se.ifmo.command.ExecuteScriptCommand} - команда для выполнения скрипта из указанного файла.</li>
 *   <li>{@link se.ifmo.command.ExitCommand} - команда для завершения программы.</li>
 *   <li>{@link se.ifmo.command.GroupCountingByMinimalPointCommand} - команда для группировки
 *   элементов коллекции по значению поля minimalPoint.</li>
 *   <li>{@link se.ifmo.command.HelpCommand} - команда для вывода справки по доступным командам.</li>
 *   <li>{@link se.ifmo.command.InfoCommand} - команда для вывода информации о коллекции.</li>
 *   <li>{@link se.ifmo.command.InsertAtIndexCommand} - команда для добавления нового элемента в заданную позицию.</li>
 *   <li>{@link se.ifmo.command.MinByMinimalPointCommand} - команда для вывода объекта с
 *   минимальным значением поля minimalPoint.</li>
 *   <li>{@link se.ifmo.command.RemoveByIdCommand} - команда для удаления элемента из коллекции по его id.</li>
 *   <li>{@link se.ifmo.command.RemoveFirstCommand} - команда для удаления первого элемента из коллекции.</li>
 *   <li>{@link se.ifmo.command.SaveCommand} - команда для сохранения коллекции в файл.</li>
 *   <li>{@link se.ifmo.command.ShowCommand} - команда для вывода всех элементов коллекции.</li>
 *   <li>{@link se.ifmo.command.SortCommand} - команда для сортировки коллекции в естественном порядке.</li>
 *   <li>{@link se.ifmo.command.UpdateIdCommand} - команда для обновления значения элемента коллекции по его id.</li>
 *   <li>{@link se.ifmo.command.WithoutParametersCommand} - команда, не требующая параметров для выполнения.</li>
 * </ul>
 */
package se.ifmo.command;