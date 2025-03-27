package com.example.movies_api.template;

import com.example.movies_api.dto.MovieDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Concrete implementation of DataExportTemplate for XML format.
 */
@Component
public class XMLExporter extends DataExportTemplate<MovieDto> {

    private final XmlMapper xmlMapper;
    
    public XMLExporter() {
        this.xmlMapper = new XmlMapper();
    }
    
    @Override
    protected String formatData(List<MovieDto> data) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sb.append("<movies>\n");
            
            for (MovieDto movie : data) {
                String movieXml = xmlMapper.writeValueAsString(movie)
                    .replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                sb.append("  ").append(movieXml).append("\n");
            }
            
            sb.append("</movies>");
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error formatting data to XML", e);
        }
    }
    
    @Override
    protected void validateData(List<MovieDto> data) {
        super.validateData(data);
        // XML-specific validations
        for (MovieDto movie : data) {
            if (movie.getId() == null) {
                throw new IllegalArgumentException("Movie ID is required for XML export");
            }
        }
    }
    
    @Override
    protected String postProcess(String fileName) {
        return "XML export completed successfully to " + fileName + 
               " with proper XML structure and encoding.";
    }
}
