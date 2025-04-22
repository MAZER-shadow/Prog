package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLogin;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;

public class RegistrationCommand extends WithoutParametersCommand {
    private final Reader reader;
    public RegistrationCommand(Reader reader, Writer writer) {
        super(CommandConfiguration.REGISTRATION_NAME, CommandConfiguration.REGISTRATION_DESCRIPTION, writer);
        this.reader = reader;
    }

    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        String [] data = getData();
        String name = data[0];
        String password = data[1];
        return RequestLogin.builder()
                .commandName(CommandConfiguration.REGISTRATION_NAME)
                .username(name)
                .password(password)
                .build();
    }

    @Override
    public void handleResponse(Response response) {
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
