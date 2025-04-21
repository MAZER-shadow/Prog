package ru.ifmo.se.client.util;

import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.io.Writer;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class LabWorkDtoPrinter {

    public void printLabWorks(List<LabWorkDto> labWorks, Writer writer) {

        // Определяем максимальные длины для каждого поля
        int maxIdLen = 3; // "ID"
        int maxNameLen = 4; // "Name"
        int maxXLen = 1; // "X"
        int maxYLen = 1; // "Y"
        int maxDateLen = 15; // "Creation Date"
        int maxMinLen = 15; // "Minimal Point"
        int maxMaxLen = 15; // "Maximum Point"
        int maxDiffLen = 15; // "Difficulty"
        int maxAuthorNameLen = 15; // "Author Name"
        int maxHeightLen = 6; // "Height"
        int maxPassportLen = 11; // "Passport ID"

        // Вычисляем максимальные длины
        for (LabWorkDto lab : labWorks) {
            maxIdLen = Math.max(maxIdLen, String.valueOf(lab.getId()).length());
            maxNameLen = Math.max(maxNameLen, lab.getName().length());

            if (lab.getCoordinates() != null) {
                maxXLen = Math.max(maxXLen, String.valueOf(lab.getCoordinates().getX()).length());
                maxYLen = Math.max(maxYLen, String.valueOf(lab.getCoordinates().getY()).length());
            }

            if (lab.getCreationDate() != null) {
                maxDateLen = Math.max(maxDateLen, lab.getCreationDate()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE).length());
            }

            maxMinLen = Math.max(maxMinLen, String.format("%.1f", lab.getMinimalPoint()).length());

            if (lab.getMaximumPoint() != null) {
                maxMaxLen = Math.max(maxMaxLen, String.format("%.1f", lab.getMaximumPoint()).length());
            }

            if (lab.getDifficulty() != null) {
                maxDiffLen = Math.max(maxDiffLen, lab.getDifficulty().toString().length());
            }

            if (lab.getAuthor() != null) {
                maxAuthorNameLen = Math.max(maxAuthorNameLen, lab.getAuthor().getName()
                        != null ? lab.getAuthor().getName().length() : 4);
                maxHeightLen = Math.max(maxHeightLen, lab.getAuthor().getHeight()
                        != null ? String.valueOf(lab.getAuthor().getHeight()).length() : 4);
                maxPassportLen = Math.max(maxPassportLen, lab.getAuthor().getPassportID()
                        != null ? lab.getAuthor().getPassportID().length() : 4);
            }
        }

        // Форматируем шапку таблицы
        String headerFormat = "┌─%s─┬─%s─┬─%s─┬─%s─┬─%s─┬─%s─┬─%s─┬─%s─┬─%s─┬─%s─┬─%s─┐%n";
        String header = String.format(headerFormat,
                repeat("─", maxIdLen),
                repeat("─", maxNameLen),
                repeat("─", maxXLen),
                repeat("─", maxYLen),
                repeat("─", maxDateLen),
                repeat("─", maxMinLen),
                repeat("─", maxMaxLen),
                repeat("─", maxDiffLen),
                repeat("─", maxAuthorNameLen),
                repeat("─", maxHeightLen),
                repeat("─", maxPassportLen));

        writer.print(header);

        // Выводим заголовки столбцов
        String titleFormat = "│ %-" + maxIdLen + "s │ %-" + maxNameLen + "s │ %-"
                + maxXLen + "s │ %-" + maxYLen + "s │ %-" + maxDateLen + "s │ %-"
                + maxMinLen + "s │ %-" + maxMaxLen + "s │ %-" + maxDiffLen + "s │ %-"
                + maxAuthorNameLen + "s │ %-" + maxHeightLen + "s │ %-" + maxPassportLen + "s │%n";
        writer.print(String.format(titleFormat,
                "ID",
                "Name",
                "X",
                "Y",
                "Creation Date",
                "Minimal Point",
                "Maximum Point",
                "Difficulty",
                "Author Name",
                "Height",
                "Passport ID"));

        // Разделитель между заголовком и данными
        String divider = String.format("├─%s─┼─%s─┼─%s─┼─%s─┼─%s─┼─%s─┼─%s─┼─%s─┼─%s─┼─%s─┼─%s─┤%n",
                repeat("─", maxIdLen),
                repeat("─", maxNameLen),
                repeat("─", maxXLen),
                repeat("─", maxYLen),
                repeat("─", maxDateLen),
                repeat("─", maxMinLen),
                repeat("─", maxMaxLen),
                repeat("─", maxDiffLen),
                repeat("─", maxAuthorNameLen),
                repeat("─", maxHeightLen),
                repeat("─", maxPassportLen));
        writer.print(divider);

        // Выводим данные
        String rowFormat = "│ %-" + maxIdLen + "d │ %-" + maxNameLen + "s │ %"
                + maxXLen + "d │ %" + maxYLen + "d │ %-" + maxDateLen + "s │ %"
                + maxMinLen + ".1f │ %" + maxMaxLen + "s │ %-" + maxDiffLen + "s │ %-"
                + maxAuthorNameLen + "s │ %" + maxHeightLen + "s │ %-" + maxPassportLen + "s │%n";
        for (LabWorkDto lab : labWorks) {
            writer.print(String.format(rowFormat,
                    lab.getId(),
                    lab.getName(),
                    lab.getCoordinates() != null ? lab.getCoordinates().getX() : 0,
                    lab.getCoordinates() != null ? lab.getCoordinates().getY() : 0,
                    lab.getCreationDate() != null ? lab.getCreationDate().format
                            (DateTimeFormatter.ISO_LOCAL_DATE) : "null",
                    lab.getMinimalPoint(),
                    lab.getMaximumPoint() != null ? String.format("%.1f", lab.getMaximumPoint()) : "null",
                    lab.getDifficulty() != null ? lab.getDifficulty().toString() : "null",
                    lab.getAuthor() != null && lab.getAuthor().getName()
                            != null ? lab.getAuthor().getName() : "null",
                    lab.getAuthor() != null && lab.getAuthor().getHeight()
                            != null ? String.valueOf(lab.getAuthor().getHeight()) : "null",
                    lab.getAuthor() != null && lab.getAuthor().getPassportID()
                            != null ? lab.getAuthor().getPassportID() : "null"));
        }

        // Нижняя граница таблицы
        String footer = String.format("└─%s─┴─%s─┴─%s─┴─%s─┴─%s─┴─%s─┴─%s─┴─%s─┴─%s─┴─%s─┴─%s─┘%n",
                repeat("─", maxIdLen),
                repeat("─", maxNameLen),
                repeat("─", maxXLen),
                repeat("─", maxYLen),
                repeat("─", maxDateLen),
                repeat("─", maxMinLen),
                repeat("─", maxMaxLen),
                repeat("─", maxDiffLen),
                repeat("─", maxAuthorNameLen),
                repeat("─", maxHeightLen),
                repeat("─", maxPassportLen));
        writer.print(footer);
    }

    private static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
}