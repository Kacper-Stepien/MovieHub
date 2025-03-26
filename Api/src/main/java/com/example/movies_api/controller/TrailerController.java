package com.example.movies_api.controller;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.command.trailer.LogTrailerCreateCommand;
import com.example.movies_api.controller.trailer_adapter.JsonTrailerAdapter;
import com.example.movies_api.controller.trailer_adapter.XmlTrailerAdapter;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.facade.trailer_facade.TrailerFacade;
import com.example.movies_api.service.TrailerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/trailers")
public class TrailerController {

    private final JsonTrailerAdapter jsonTrailerAdapter;
    private final XmlTrailerAdapter xmlTrailerAdapter;
    private final TrailerService trailerService;
    private final TrailerFacade trailerFacade;

    public TrailerController(JsonTrailerAdapter jsonTrailerAdapter,XmlTrailerAdapter xmlTrailerAdapter,TrailerService trailerService,TrailerFacade trailerFacade) {
        this.jsonTrailerAdapter = jsonTrailerAdapter;
        this.xmlTrailerAdapter = xmlTrailerAdapter;
        this.trailerService = trailerService;
        this.trailerFacade = trailerFacade;
    }

    // JSON: Add Trailer
    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"},produces = {"application/json", "application/xml"})
    public ResponseEntity<String> addTrailer(@RequestBody TrailerDto trailer, @RequestHeader("Accept") String acceptHeader) throws Exception {
        String response = "application/xml".equalsIgnoreCase(acceptHeader)
                ? xmlTrailerAdapter.addTrailer(trailer)
                : jsonTrailerAdapter.addTrailer(trailer);

        // Wzorzec Command – logowanie operacji
        LogCommand logCommand = new LogTrailerCreateCommand(trailer);
        logCommand.execute();

        URI savedTrailerUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(trailer.getId())
                .toUri();
        return ResponseEntity.created(savedTrailerUri).body(response);
    }


    // JSON: Get all trailers
    @GetMapping(value = "/all")
    public ResponseEntity<List<TrailerDto>> getTrailers(@RequestParam(required = false, defaultValue = "local") String source) throws Exception {
        return ResponseEntity.ok(trailerService.getTrailers(source));
    }

    /**
     * Search trailers using the Interpreter pattern
     * Example query: "TITLE action OR DESCRIPTION exciting"
     */
    @GetMapping("/search")
    public ResponseEntity<List<TrailerDto>> searchTrailers(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "local") String source) {
        try {
            return ResponseEntity.ok(trailerService.searchTrailers(query, source));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid search query: " + e.getMessage());
        }
    }


    // Fasada – pobierz wszystkie trailery z logiką fallbacku
    @GetMapping("/facade/all-trailer")
    public ResponseEntity<List<TrailerDto>> getAllTrailersViaFacade() {
        return ResponseEntity.ok(trailerFacade.getAllTrailers());
    }
}

/*
// before trailer bridge (that bridge will get the external data if the client wants it)
@RestController
@RequestMapping("/trailers")
public class TrailerController {

    private final JsonTrailerAdapter jsonTrailerAdapter;
    private final XmlTrailerAdapter xmlTrailerAdapter;

    public TrailerController(JsonTrailerAdapter jsonTrailerAdapter,XmlTrailerAdapter xmlTrailerAdapter) {
        this.jsonTrailerAdapter = jsonTrailerAdapter;
        this.xmlTrailerAdapter = xmlTrailerAdapter;
    }

    // JSON: Add Trailer
    @PostMapping(value = "/add", consumes = {"application/json", "application/xml"},produces = {"application/json", "application/xml"})
    public ResponseEntity<String> addTrailer(@RequestBody TrailerDto trailer, @RequestHeader("Accept") String acceptHeader) throws Exception {
        String response = "application/xml".equalsIgnoreCase(acceptHeader)
                ? xmlTrailerAdapter.addTrailer(trailer)
                : jsonTrailerAdapter.addTrailer(trailer);
        URI savedTrailerUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(trailer.getId())
                .toUri();
        return ResponseEntity.created(savedTrailerUri).body(response);
    }


    // JSON: Get all trailers
    @GetMapping(value = "/all")
    public ResponseEntity<List<TrailerDto>> getAllTrailers() throws Exception {
        return ResponseEntity.ok(jsonTrailerAdapter.getAllTrailers());
    }

}*/

/*
       //before trailer adapter
@RestController
@RequestMapping("/trailers")
@RequiredArgsConstructor
public class TrailerController {

    private final TrailerService trailerService;

    @PostMapping("/add")
    public ResponseEntity<TrailerDto> addTrailer(@RequestBody TrailerDto trailerDto) {
        return ResponseEntity.ok(trailerService.addTrailer(trailerDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrailerDto>> getAllTrailers() {
        return ResponseEntity.ok(trailerService.findAllTrailers());
    }
}
*/