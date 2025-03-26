package com.example.movies_api.command;

import com.example.movies_api.logger.FileLogWriter;

public interface LogCommand {
    void execute(FileLogWriter writer);
}