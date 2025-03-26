package com.example.movies_api.command.user;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.logger.FileLogWriter;

public class LogUserDetailsUpdateCommand implements LogCommand {
    private final String userEmail;

    public LogUserDetailsUpdateCommand(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public void execute(FileLogWriter writer) {
        writer.writeToLog("users.log", "Zaktualizowano dane użytkownika: " + userEmail);
    }
}
