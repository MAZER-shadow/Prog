package ru.ifmo.se.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import ru.ifmo.se.server.entity.User;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final SecretKey secretKey; // Изменили тип с Key на SecretKey
    private final MD5HashingService md5HashingService = new MD5HashingService(new String("eRGOKMEFGLKMDRG"));

    public void register(String name, String password) throws AuthenticationException {
        Optional<User> user = userService.findByUsername(name);
        if (user.isPresent()) {
            throw new AuthenticationException("Username already exists.");
        }
        userService.save(User.builder()
                .name(name)
                .password(md5HashingService.hashString(password))
                .build());
    }

    public String login(String name, String password) throws AuthenticationException {
        Optional<User> user = userService.findByUsername(name);

        if (user.isEmpty() || !user.get().getPassword().equals(md5HashingService.hashString(password))) {
            throw new AuthenticationException("Invalid username or password.");
        }
        return generateToken(user.get());
    }

    public User authenticate(String token) throws AuthenticationException {
        if (token == null || token.isEmpty()) {
            throw new AuthenticationException("Invalid token: Token is missing.");
        }

        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey) // Теперь без приведения типа
                    .build()
                    .parseSignedClaims(token);
            Long userId = jws.getBody().get("userId", Long.class);
            Optional<User> user = userService.findById(userId);

            if (user.isEmpty()) {
                throw new AuthenticationException("Invalid token: User not found.");
            }

            return user.get(); // Используем .get() вместо .orElse(null)

        } catch (JwtException e) {
            throw new AuthenticationException("Invalid token: " + e.getMessage());
        }
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                .signWith(secretKey)
                .compact();
    }
}