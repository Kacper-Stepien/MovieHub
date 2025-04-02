package com.example.movies_api.logger;

import com.example.movies_api.command.LogCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

//3/3 command pattern - added command receiver and command invoker
//[previously] that file did not exist
public class CommandLogger {
    private final FileLogWriter logWriter = new FileLogWriter();

    public void execute(LogCommand command) {
        command.execute(logWriter);
    }
}