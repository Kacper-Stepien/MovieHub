package com.example.movies_api.logger;

import com.example.movies_api.command.LogCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CommandLogger {
    private final FileLogWriter logWriter = new FileLogWriter();

    public void execute(LogCommand command) {
        command.execute(logWriter);
    }
}