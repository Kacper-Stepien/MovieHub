package com.example.movies_api.collection;

import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionItemRepository collectionItemRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public Long createCollectionGroup(String name, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        CollectionGroup group = new CollectionGroup(name, owner);
        group = collectionItemRepository.save(group);
        return group.getId();
    }

    public Long createMovieItem(String name, Long movieId, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new BadRequestException("Nie ma takiego filmu: " + movieId));

        MovieItem item = new MovieItem(name, owner, movie);
        item = collectionItemRepository.save(item);
        return item.getId();
    }

    public void addChild(Long parentId, Long childId, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        CollectionItem parent = collectionItemRepository.findById(parentId)
                .orElseThrow(() -> new BadRequestException("Brak parenta=" + parentId));
        CollectionItem child = collectionItemRepository.findById(childId)
                .orElseThrow(() -> new BadRequestException("Brak childa=" + childId));

        if (!parent.getOwner().equals(owner) || !child.getOwner().equals(owner)) {
            throw new BadRequestException("Brak uprawnień. Ten user nie jest właścicielem parenta/childa.");
        }

        if (parent instanceof CollectionGroup group) {
            group.addItem(child);
            collectionItemRepository.save(group);
        } else {
            throw new BadRequestException("Parent nie jest folderem");
        }
    }

    public String showCollection(Long id, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        CollectionItem item = collectionItemRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Brak itemu=" + id));

        if (!item.getOwner().equals(owner)) {
            throw new BadRequestException("To nie jest Twoja kolekcja");
        }
        return item.show("");
    }

    public int countMovies(Long id, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        CollectionItem item = collectionItemRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono itemu=" + id));

        if (!item.getOwner().equals(owner)) {
            throw new BadRequestException("To nie jest Twoja kolekcja");
        }
        return item.countMovies();
    }

    public List<CollectionItem> getAllItemsForUser(String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        return collectionItemRepository.findByOwner(owner);
    }

    public CollectionItemTreeDto buildTree(CollectionItem item) {
        CollectionItemTreeDto dto = new CollectionItemTreeDto();
        dto.setId(item.getId());
        dto.setName(item.getName());

        if (item instanceof MovieItem mi) {
            dto.setType("MOVIE");
            if (mi.getMovie() != null) {
                dto.setMovieId(mi.getMovie().getId());
            }
        } else if (item instanceof CollectionGroup cg) {
            dto.setType("GROUP");
            for (CollectionItem child : cg.getChildren()) {
                dto.getChildren().add(buildTree(child));
            }
        }
        return dto;
    }

    public CollectionItemTreeDto getTree(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Brak usera"));

        CollectionItem root = collectionItemRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Brak itemu=" + id));

        if (!root.getOwner().equals(user)) {
            throw new BadRequestException("To nie jest Twoja kolekcja");
        }
        return buildTree(root);
    }

    public List<CollectionItemTreeDto> getAllRootTrees(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Brak usera"));
        List<CollectionItem> all = collectionItemRepository.findByOwner(user);
        Set<Long> childIds = new HashSet<>();
        for (CollectionItem c : all) {
            if (c instanceof CollectionGroup cg) {
                for (CollectionItem child : cg.getChildren()) {
                    childIds.add(child.getId());
                }
            }
        }
        List<CollectionItemTreeDto> result = new ArrayList<>();
        for (CollectionItem c : all) {
            if (!childIds.contains(c.getId())) {
                result.add(buildTree(c));
            }
        }
        return result;
    }

    // Iterator 3 ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<CollectionItem> getFlattenedItems(Long rootId, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadRequestException("Nie znaleziono usera o email=" + userEmail));

        CollectionItem root = collectionItemRepository.findById(rootId)
                .orElseThrow(() -> new BadRequestException("Brak itemu=" + rootId));

        if (!root.getOwner().equals(owner)) {
            throw new BadRequestException("To nie jest Twoja kolekcja");
        }

        if (!(root instanceof CollectionGroup)) {
            return List.of(root);
        }

        List<CollectionItem> flattened = new ArrayList<>();
        for (CollectionItem item : (CollectionGroup) root) {
            flattened.add(item);
        }
        return flattened;
    }

}
