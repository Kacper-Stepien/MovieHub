package com.example.movies_api.collection;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping("/create-folder")
    public ResponseEntity<Long> createFolder(@RequestParam String name) {
        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Long folderId = collectionService.createCollectionGroup(name, currentUserEmail);
        return ResponseEntity.ok(folderId);
    }

    @PostMapping("/create-movie-item")
    public ResponseEntity<Long> createMovieItem(@RequestParam String name,
                                                @RequestParam Long movieId) {
        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Long itemId = collectionService.createMovieItem(name, movieId, currentUserEmail);
        return ResponseEntity.ok(itemId);
    }

    @PostMapping("/add-child")
    public ResponseEntity<Void> addChild(@RequestParam Long parentId,
                                         @RequestParam Long childId) {
        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        collectionService.addChild(parentId, childId, currentUserEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<String> show(@PathVariable Long id) {
        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        String result = collectionService.showCollection(id, currentUserEmail);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/count")
    public ResponseEntity<Integer> countMovies(@PathVariable Long id) {
        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        int count = collectionService.countMovies(id, currentUserEmail);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<CollectionItemDto>> getMyCollections() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CollectionItem> items = collectionService.getAllItemsForUser(currentUserEmail);

        List<CollectionItemDto> dtos = items.stream()
                .map(item -> CollectionItemDto.fromEntity(item))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/tree")
    public ResponseEntity<CollectionItemTreeDto> getCollectionTree(@PathVariable Long id) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        CollectionItemTreeDto dto = collectionService.getTree(id, currentUserEmail);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all-tree")
    public ResponseEntity<List<CollectionItemTreeDto>> getAllRootTrees() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CollectionItemTreeDto> trees = collectionService.getAllRootTrees(email);
        return ResponseEntity.ok(trees);
    }

}
