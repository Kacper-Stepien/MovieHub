package com.example.movies_api.controller;

import com.example.movies_api.dto.CommentDto;
import com.example.movies_api.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<CommentDto>> getCommentsByMovieId(@PathVariable long movieId) {
        List<CommentDto> comments = commentService.getCommentsByMovieId(movieId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/add-comment/{movieId}")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable long movieId,
            @Valid
            @RequestBody CommentDto commentDto
    ) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        CommentDto savedComment = commentService.addOrUpdateComment(currentUserEmail, movieId, commentDto.getContent());
        URI savedCommentUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedComment.getId())
                .toUri();
        return ResponseEntity.created(savedCommentUri).body(savedComment);
    }


    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.deleteComment(id, currentUserEmail);
        return ResponseEntity.noContent().build();
    }
}