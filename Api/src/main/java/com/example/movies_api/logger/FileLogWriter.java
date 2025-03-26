package com.example.movies_api.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FileLogWriter {
    private static final String LOG_DIRECTORY = "api_logs";

    public void writeToLog(String fileName, String message) {
        try {
            Files.createDirectories(Paths.get(LOG_DIRECTORY));
            String path = LOG_DIRECTORY + "/" + fileName;
            try (FileWriter writer = new FileWriter(path, true)) {
                writer.write(LocalDateTime.now() + " - " + message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
