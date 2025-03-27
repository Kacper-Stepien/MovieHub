package com.example.movies_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.movies_api.dto.CommentDto;
import com.example.movies_api.events.EventMediator;
import com.example.movies_api.events.EventType;
import com.example.movies_api.events.MediatorConfig;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.model.Comment;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.CommentRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MediatorConfig.MEDIATOR = new EventMediator() {
            @Override
            public void notify(Object sender, EventType eventType) {
                // no-op
            }
        };
    }


    @Test
    void addOrUpdateComment_shouldCreateNewCommentIfNotExists() {
        String userEmail = "test@example.com";
        long movieId = 1L;
        String content = "Great movie!";
        User user = new User();
        user.setEmail(userEmail);
        Movie movie = new Movie();
        movie.setId(movieId);
        Comment newComment = new Comment();

        when(commentRepository.findByUser_EmailAndMovie_Id(userEmail, movieId))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmail(userEmail))
                .thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId))
                .thenReturn(Optional.of(movie));

        CommentDto result = commentService.addOrUpdateComment(userEmail, movieId, content);

        assertEquals(content, result.getContent());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void addOrUpdateComment_shouldUpdateExistingComment() {
        String userEmail = "user@example.com";
        long movieId = 1L;
        String newContent = "Updated comment";
        User user = new User();
        user.setEmail(userEmail);
        Movie movie = new Movie();
        movie.setId(movieId);
        Comment existingComment = new Comment();
        existingComment.setUser(user);
        existingComment.setMovie(movie);

        when(commentRepository.findByUser_EmailAndMovie_Id(userEmail, movieId))
                .thenReturn(Optional.of(existingComment));
        when(userRepository.findByEmail(userEmail))
                .thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId))
                .thenReturn(Optional.of(movie));

        CommentDto dto = commentService.addOrUpdateComment(userEmail, movieId, newContent);

        assertEquals(newContent, dto.getContent());
        verify(commentRepository).save(existingComment);
    }

    @Test
    void deleteComment_shouldThrowIfUserIsNotOwner() {
        Comment comment = new Comment();
        User user = new User();
        user.setEmail("owner@example.com");
        comment.setUser(user);
        comment.setId(10L);

        when(commentRepository.findById(10L)).thenReturn(Optional.of(comment));

        assertThrows(BadRequestException.class,
                () -> commentService.deleteComment(10L, "intruder@example.com"));
    }

    @Test
    void getCommentsByMovieId_shouldReturnListOfCommentDtos() {
        long movieId = 1L;
        User user = new User();
        user.setId(100L);
        Comment comment = new Comment();
        comment.setId(5L);
        comment.setContent("Nice");
        comment.setUser(user);
        List<Comment> comments = List.of(comment);

        when(movieRepository.existsById(movieId)).thenReturn(true);
        when(commentRepository.findByMovieId(movieId)).thenReturn(comments);

        List<CommentDto> result = commentService.getCommentsByMovieId(movieId);

        assertEquals(1, result.size());
        assertEquals("Nice", result.get(0).getContent());
        assertEquals(100L, result.get(0).getUserId());
    }

    @Test
    void getCommentsByMovieId_shouldThrowIfMovieDoesNotExist() {
        long movieId = 99L;
        when(movieRepository.existsById(movieId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> commentService.getCommentsByMovieId(movieId));
    }
}
