package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.factory.VideoFactory;
import com.example.movies_api.interpreter.trailer_search.TrailerSearchContext;
import com.example.movies_api.interpreter.trailer_search.TrailerSearchExpression;
import com.example.movies_api.interpreter.trailer_search.TrailerSearchParser;
import com.example.movies_api.mapper.TrailerDtoMapper;
import com.example.movies_api.memento.trailer_memento.TrailerListCaretaker;
import com.example.movies_api.memento.trailer_memento.TrailerListMemento;
import com.example.movies_api.model.Trailer;
import com.example.movies_api.proxy.TrailerDataProxy;
import com.example.movies_api.repository.TrailerRepository;
import com.example.movies_api.trailer_data_provider.ExternalTrailerProvider;
import com.example.movies_api.trailer_data_provider.LocalTrailerProvider;
import com.example.movies_api.trailer_data_provider.TrailerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrailerService {

    private final TrailerRepository trailerRepository;

    private final LocalTrailerProvider localTrailerProvider;
    private final ExternalTrailerProvider externalTrailerProvider;
    private final TrailerDataProxy trailerDataProxy;
    private final TrailerListCaretaker trailerListCaretaker;


    public TrailerDto addTrailer(TrailerDto trailerDto) {
        if (trailerDto.getYoutubeTrailerId() == null || trailerDto.getYoutubeTrailerId().isEmpty()) {
            throw new BadRequestException("YouTube ID cannot be empty");
        }

        // 💾 Zapisz poprzedni stan
        trailerListCaretaker.save(new TrailerListMemento(trailerRepository.findAll()));


        Trailer trailer = VideoFactory.createTrailer(
                trailerDto.getTitle(),
                trailerDto.getYoutubeTrailerId(),
                trailerDto.getDescription(),
                trailerDto.getThumbnail()
        );

        trailer = trailerRepository.save(trailer);

        return new TrailerDto(trailer.getId(),
                trailer.getTitle(),
                trailer.getYoutubeTrailerId(),
                trailer.getDescription(),
                trailer.getThumbnail());
    }

    /*
        //copy from before implementing memento for trailer list restoring
    public TrailerDto addTrailer(TrailerDto trailerDto) {
        if (trailerDto.getYoutubeTrailerId() == null || trailerDto.getYoutubeTrailerId().isEmpty()) {
            throw new BadRequestException("YouTube ID cannot be empty");
        }

        Trailer trailer = VideoFactory.createTrailer(
                trailerDto.getTitle(),
                trailerDto.getYoutubeTrailerId(),
                trailerDto.getDescription(),
                trailerDto.getThumbnail()
        );

        trailer = trailerRepository.save(trailer);

        return new TrailerDto(trailer.getId(),
                trailer.getTitle(),
                trailer.getYoutubeTrailerId(),
                trailer.getDescription(),
                trailer.getThumbnail());
    }
    * */

    //depricated -- after adding trailer bridge
    public List<TrailerDto> findAllTrailers() {
        return trailerRepository.findAll().stream()
                .map(TrailerDtoMapper::map)
                .collect(Collectors.toList());
    }


    public List<TrailerDto> getTrailers(String source) {
        // Using the proxy to get trailers
        if (source.equalsIgnoreCase("external")) {
            return trailerDataProxy.getTrailersFromSource("external");
        }
        return trailerDataProxy.getTrailersFromSource("local");
    }

    /**
     * Search trailers using a query expression (Interpreter pattern)
     * Example: "TITLE action OR DESCRIPTION exciting"
     * 
     * @param searchQuery the query expression to evaluate
     * @param source the source of trailers (local or external)
     * @return list of trailers that match the query
     */
    public List<TrailerDto> searchTrailers(String searchQuery, String source) {
        // Get trailers to search through
        List<TrailerDto> allTrailers = getTrailers(source);
        TrailerSearchContext context = new TrailerSearchContext(allTrailers);
        
        // Parse the search query and interpret it
        TrailerSearchParser parser = new TrailerSearchParser();
        TrailerSearchExpression expression = parser.parse(searchQuery);
        
        return expression.interpret(context);
    }


    //memento method for restoring trailer list
    public void undoLastTrailerAdd() {
        TrailerListMemento memento = trailerListCaretaker.getLastSnapshot();
        if (memento != null) {
            trailerRepository.deleteAll();
            trailerRepository.saveAll(memento.getSavedState());
        } else {
            throw new IllegalStateException("Brak zapisanej poprzedniej wersji listy trailerów.");
        }
    }

}
