package com.example.movies_api.bad_practices;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterService {
    public void writeToFile(String content, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(content + "\n");
        } catch (IOException e) {
            System.out.println("Błąd zapisu: " + e.getMessage());
        }
    }
}
