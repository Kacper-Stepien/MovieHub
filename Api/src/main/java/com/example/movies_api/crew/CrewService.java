package com.example.movies_api.crew;

import com.example.movies_api.flyweight.RoleName;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.visitor.CrewReportVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final MovieRepository movieRepository;
    private final CrewItemRepository crewItemRepository;

    public Long initCrewRoot(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Nie ma filmu=" + movieId));

        if (movie.getCrewRoot() == null) {
            CrewGroup root = new CrewGroup("RootCrew for " + movie.getTitle());
            root.setMovie(movie); // Ustaw film w root
            crewItemRepository.save(root);

            movie.setCrewRoot(root);
            movieRepository.save(movie);
        }
        return movie.getCrewRoot().getId();
    }

    public Long addCrewMember(Long parentGroupId, String name, RoleName role) {
        CrewItem parent = crewItemRepository.findById(parentGroupId)
                .orElseThrow(() -> new RuntimeException("Brak parenta=" + parentGroupId));
        if (!(parent instanceof CrewGroup group)) {
            throw new RuntimeException("parent nie jest grupą");
        }

        CrewMember member = new CrewMember(name, role);
        member.setMovie(parent.getMovie());

        group.addItem(member);
        crewItemRepository.save(member);
        crewItemRepository.save(group);
        return member.getId();
    }


    public Long addCrewGroup(Long parentGroupId, String groupName) {
        CrewItem parent = crewItemRepository.findById(parentGroupId)
                .orElseThrow(() -> new RuntimeException("Brak parenta=" + parentGroupId));
        if (!(parent instanceof CrewGroup group)) {
            throw new RuntimeException("parent nie jest grupą");
        }

        CrewGroup subGroup = new CrewGroup(groupName);
        subGroup.setMovie(parent.getMovie());

        group.addItem(subGroup);
        crewItemRepository.save(subGroup);
        crewItemRepository.save(group);
        return subGroup.getId();
    }

    public String showCrewTree(Long itemId) {
        CrewItem item = crewItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Brak itemu=" + itemId));
        return item.show("");
    }

    public int countCrew(Long itemId) {
        CrewItem item = crewItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Brak itemu=" + itemId));
        return item.countMembers();
    }

    // Iterator 2 użycie ///////////////////////////////////////////////////////////////////////////////////////////////
    public List<CrewItem> getAllCrewItems(Long rootGroupId) {
        CrewItem root = crewItemRepository.findById(rootGroupId)
                .orElseThrow(() -> new RuntimeException("Brak root groupa=" + rootGroupId));

        if (!(root instanceof CrewGroup group)) {
            throw new RuntimeException("Root nie jest CrewGroup!");
        }

        List<CrewItem> flattened = new ArrayList<>();
        for (CrewItem item : group) {
            flattened.add(item);
        }
        return flattened;
    }

    // Visitor 1 użycie ////////////////////////////////////////////////////////////////////////////////////////////////
    public List<String> generateCrewReport(Long rootGroupId) {
        CrewItem root = crewItemRepository.findById(rootGroupId)
                .orElseThrow(() -> new RuntimeException("Brak grupy o id " + rootGroupId));
        if (!(root instanceof CrewGroup)) {
            throw new RuntimeException("Podany item nie jest grupą");
        }
        CrewReportVisitor visitor = new CrewReportVisitor();
        ((CrewGroup) root).accept(visitor);
        return visitor.getReport();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
