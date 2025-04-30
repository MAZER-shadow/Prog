package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLogin;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.AuthService;

import javax.naming.AuthenticationException;

public class RegistrationCommand extends AbstractCommand {
    private final AuthService authService;
    public RegistrationCommand(AuthService authService) {
        super(CommandConfiguration.REGISTRATION_NAME, CommandConfiguration.REGISTRATION_DESCRIPTION, Condition.INSECURE);
        this.authService = authService;
    }

    @Override
    public Response execute(Request request, User user) {
        try {
            if (request instanceof RequestLogin requestLogin) {
                String name = requestLogin.getUsername();
                String password = requestLogin.getPassword();
                authService.register(name, password);
                return Response.builder()
                        .answerType(AnswerType.SUCCESS)
                        .message("Вы успешно зарегестрировались")
                        .build();
            }
        } catch (AuthenticationException e) {
            return Response.builder()
                    .answerType(AnswerType.ERROR)
                    .message("Имя уже занято, извините")
                    .build();
        }
        return Response.builder().answerType(AnswerType.ERROR).build();
    }
}
