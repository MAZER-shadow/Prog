package ru.ifmo.se.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.exception.AuthenticationRuntimeException;
import ru.ifmo.se.server.exception.TokenTimeRuntimeException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    public static final String E_RGOKMEFGLKMDRG = "eRGOKMEFGLKMDRG";
    private final UserService userService;
    private final SecretKey secretKey;
    private final MD5HashingService md5HashingService = new MD5HashingService(E_RGOKMEFGLKMDRG);

    public void register(String name, String password) {
        Optional<User> user = userService.findByUsername(name);
        if (user.isPresent()) {
            throw new AuthenticationRuntimeException("Имя уже занято");
        }
        userService.save(User.builder()
                .name(name)
                .password(md5HashingService.hashString(password))
                .build());
    }

    public String login(String name, String password) {
        Optional<User> user = userService.findByUsername(name);

        if (user.isEmpty() || !user.get().getPassword().equals(md5HashingService.hashString(password))) {
            throw new AuthenticationRuntimeException("Неверное имя или пароль");
        }
        return generateToken(user.get());
    }

    public User authenticate(String token) {
        if (token == null || token.isEmpty()) {
            throw new AuthenticationRuntimeException("Вы ещё не авторизовались, чтобы использовать такие команды");
        }

        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            Long userId = jws.getBody().get("userId", Long.class);
            Optional<User> user = userService.findById(userId);

            if (user.isEmpty()) {
                throw new AuthenticationRuntimeException("Неверный токен. Пользователь не найден");
            }

            return user.get();

        } catch (JwtException e) {
            throw new TokenTimeRuntimeException("время действия токена истекло, нужно авторизоваться по новой");
        }
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000000000))
                .signWith(secretKey)
                .compact();
    }
}