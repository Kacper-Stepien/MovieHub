package com.example.movies_api.template;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Abstract template class that defines the skeleton of data export algorithm.
 * Uses the Template Method pattern to define the steps of the export process.
 */
public abstract class DataExportTemplate<T> {
    
    /**
     * Template method that defines the algorithm for exporting data.
     * This method is final so that subclasses cannot override it.
     */
    public final String exportData(List<T> data, String fileName) throws IOException {
        validateData(data);
        String formattedData = formatData(data);
        writeToFile(formattedData, fileName);
        return postProcess(fileName);
    }
    
    /**
     * Validates the data before export.
     * Default implementation checks if data is not null or empty.
     * Subclasses can override to provide specific validations.
     */
    protected void validateData(List<T> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be null or empty");
        }
    }
    
    /**
     * Hook method for post-processing after export.
     * Default implementation returns a success message.
     * Subclasses can override to provide specific post-processing.
     */
    protected String postProcess(String fileName) {
        return "Data successfully exported to " + fileName;
    }
    
    /**
     * Writes formatted data to a file.
     * This is a concrete method used by all subclasses.
     */
    private void writeToFile(String formattedData, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(formattedData);
        }
    }
    
    /**
     * Abstract method to format data according to specific export type.
     * Must be implemented by concrete subclasses.
     */
    protected abstract String formatData(List<T> data);
}
