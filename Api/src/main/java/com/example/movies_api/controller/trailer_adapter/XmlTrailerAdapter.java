package com.example.movies_api.controller.trailer_adapter;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.service.TrailerService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("xmlTrailerAdapter")
public class XmlTrailerAdapter implements TrailerAdapter {

    private final TrailerService trailerService;
    private final XmlMapper xmlMapper = new XmlMapper();

    public XmlTrailerAdapter(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    @Override
    public String addTrailer(TrailerDto xmlTrailer) throws Exception {
        trailerService.addTrailer(xmlTrailer);
        return "<response><message>Trailer added successfully</message></response>";
    }

    @Override
    public List<TrailerDto> getAllTrailers() throws Exception {
        return trailerService.findAllTrailers();
    }

    //Open-Close Principle 2/3 (data steering) [added lines]
    @Override
    public String getSupportedContentType() {
        return "application/xml";
    }
}