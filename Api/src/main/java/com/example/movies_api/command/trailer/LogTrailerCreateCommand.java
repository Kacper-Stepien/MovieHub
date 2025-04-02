package com.example.movies_api.command.trailer;
import com.example.movies_api.command.LogCommand;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.logger.FileLogWriter;

//1/3 command pattern - the command is issued on the trailer creation
//[previously] that file did not exist
public class LogTrailerCreateCommand implements LogCommand {
    private final TrailerDto trailer;

    public LogTrailerCreateCommand(TrailerDto trailer) {
        this.trailer = trailer;
    }

    @Override
    public void execute(FileLogWriter writer) {
        writer.writeToLog("trailers.log", "Dodano trailer: " + trailer.getTitle());
    }
}
