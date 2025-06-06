package ru.ifmo.se.common.dto.model;

import java.io.Serializable;

/**
 * Перечисление Difficulty представляет уровни сложности лабораторной работы.
 * Используется для указания сложности выполнения лабораторной работы.
 */
public enum DifficultyDto implements Serializable {
    /**
     * Нормальный уровень сложности.
     */
    NORMAL,

    /**
     * Высокий уровень сложности.
     */
    HARD,

    /**
     * Очень высокий уровень сложности.
     */
    VERY_HARD,

    /**
     * Экстремальный уровень сложности.
     */
    INSANE,

    /**
     * Безнадежный уровень сложности.
     */
    HOPELESS;
}