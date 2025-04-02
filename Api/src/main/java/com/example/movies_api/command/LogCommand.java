package com.example.movies_api.command;


import com.example.movies_api.logger.FileLogWriter;


//3/3 command pattern - interface for file saving issue
//[previously] that file did not exist
public interface LogCommand {
    void execute(FileLogWriter writer);
}