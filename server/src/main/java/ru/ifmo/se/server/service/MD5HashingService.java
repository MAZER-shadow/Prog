package ru.ifmo.se.server.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class MD5HashingService {

    private final String salt;

    public MD5HashingService(String salt) {
        this.salt = salt;
    }

    public String hashString(String input) {
        try {
            String saltedInput = input + salt; // Добавляем соль к входной строке
            MessageDigest md = MessageDigest.getInstance("MD5"); // Получаем экземпляр MessageDigest для MD5
            byte[] hashBytes = md.digest(saltedInput.getBytes(StandardCharsets.UTF_8)); // Хешируем байты строки
            String hexEncodedHash = HexFormat.of().formatHex(hashBytes);  // Преобразуем байты хеша в шестнадцатеричное представление
            return hexEncodedHash;
        } catch (NoSuchAlgorithmException e) {
            // Обработка исключения, если алгоритм MD5 не поддерживается (вряд ли произойдет)
            throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }

    // Геттер для соли (если нужно)
    public String getSalt() {
        return salt;
    }
}
