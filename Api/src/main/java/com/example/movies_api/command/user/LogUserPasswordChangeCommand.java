package com.example.movies_api.command.user;


import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;

public class LogUserPasswordChangeCommand implements LogCommand {
    private final String userEmail;

    public LogUserPasswordChangeCommand(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void execute() {
        CommandLogger.log("users.log", "Zmieniono hasło użytkownika: " + userEmail);
    }
}
