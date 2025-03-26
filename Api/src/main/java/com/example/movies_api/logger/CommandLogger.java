package com.example.movies_api.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CommandLogger {
    private static final String LOG_DIRECTORY = "api_logs";
    private static final String LOG_FILE = LOG_DIRECTORY + "/trailers.log";

    public static void log(String message) {
        try {
            // Upewnij się, że katalog istnieje
            Files.createDirectories(Paths.get(LOG_DIRECTORY));

            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                writer.write(LocalDateTime.now() + " - " + message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}