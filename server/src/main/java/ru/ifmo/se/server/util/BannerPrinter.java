package ru.ifmo.se.server.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BannerPrinter {

    public static final String RESET = "\u001B[0m";

    public static void printBanner() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                BannerPrinter.class.getResourceAsStream("/banner.txt"), StandardCharsets.UTF_8))) {

            List<String> lines = reader.lines().collect(Collectors.toList());

            StringBuilder coloredBanner = new StringBuilder();

            for (int i = 0; i < lines.size(); i++) {
                double ratio = (double) i / lines.size();
                int r = (int) (255 * (1 - ratio));
                int g = 0;
                int b = (int) (255 * ratio);
                String color = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);

                coloredBanner.append(color).append(lines.get(i)).append(RESET);
                if (i < lines.size() - 1) {
                    coloredBanner.append("\n");
                }
            }

            log.info("\n" + coloredBanner);
        } catch (Exception e) {
            log.warn("Could not load banner: " + e.getMessage());
        }
    }
}
