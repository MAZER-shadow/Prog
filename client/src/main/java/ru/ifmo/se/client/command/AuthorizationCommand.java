package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLogin;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseToken;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;

public class AuthorizationCommand extends WithoutParametersCommand {
    private final Reader reader;
    public AuthorizationCommand(Reader reader, Writer writer) {
        super(CommandConfiguration.AUTHORIZATION_NAME, CommandConfiguration.AUTHORIZATION_DESCRIPTION, writer, false);
        this.reader = reader;
    }

    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        String [] data = getData();
        String name = data[0];
        String password = data[1];
        return RequestLogin.builder()
                .commandName(CommandConfiguration.AUTHORIZATION_NAME)
                .username(name)
                .password(password)
                .build();
    }

    @Override
    public void handleResponse(Response response) {
        if (response instanceof ResponseToken responseToken) {
            CommandManager.token = responseToken.getToken();
        }
        writer.println(response.getMessage());
    }

    private String [] getData() {
        writer.println("Введите ваш логин:");
        String login = reader.readLine();
        writer.println("Введите ваш пароль:");
        String password = reader.readLine();
        return new String[] {login, password};
    }
}
