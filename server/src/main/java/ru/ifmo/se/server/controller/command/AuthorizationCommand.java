package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLogin;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseToken;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.AuthService;

import javax.naming.AuthenticationException;

public class AuthorizationCommand extends AbstractCommand {
    private final AuthService authService;

    public AuthorizationCommand(AuthService authService) {
        super(CommandConfiguration.AUTHORIZATION_NAME, CommandConfiguration.AUTHORIZATION_DESCRIPTION, Condition.INSECURE);
        this.authService = authService;
    }

    @Override
    public Response execute(Request request, User user) {
        try {
            if (request instanceof RequestLogin requestLogin) {
                String name = requestLogin.getUsername();
                String password = requestLogin.getPassword();
                String token = authService.login(name, password);
                return ResponseToken.builder()
                        .status(true)
                        .message("Login Successful")
                        .token(token)
                        .build();
            }
        } catch (AuthenticationException e) {
            return Response.builder()
                    .status(false)
                    .message("Данные не валидны")
                    .build();
        }
        return Response.builder().status(false).build();
    }
}
