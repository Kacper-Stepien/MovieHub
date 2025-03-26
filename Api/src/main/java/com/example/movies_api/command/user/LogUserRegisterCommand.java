package com.example.movies_api.command.user;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.auth.RegisterRequest;

public class LogUserRegisterCommand implements LogCommand {
    private final RegisterRequest request;

    public LogUserRegisterCommand(RegisterRequest request) {
        this.request = request;
    }

    @Override
    public void execute() {
        CommandLogger.log("users.log", "Zarejestrowano użytkownika: " + request.getEmail());
    }
}
