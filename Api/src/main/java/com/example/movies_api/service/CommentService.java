package com.example.movies_api.service;

import com.example.movies_api.dto.CommentDto;
import com.example.movies_api.events.MediatorConfig;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.CommentDtoMapper;
import com.example.movies_api.model.Comment;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import com.example.movies_api.observer.notification.EmailNotificationObserver;
import com.example.movies_api.observer.notification.InAppNotificationObserver;
import com.example.movies_api.observer.notification.MovieCommentNotifier;
import com.example.movies_api.observer.notification.SmsNotificationObserver;
import com.example.movies_api.open_close.CommentValidator;
import com.example.movies_api.open_close.DefaultCommentValidator;
import com.example.movies_api.repository.CommentRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.UserRepository;
import com.example.movies_api.events.EventType;
import com.example.movies_api.stats.StatsCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.movies_api.constants.Messages.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public CommentDto addOrUpdateComment(String userEmail, long movieId, String commentContent) {
        Comment commentToSaveOrUpdate = commentRepository.findByUser_EmailAndMovie_Id(userEmail, movieId)
                .orElseGet(Comment::new);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
        commentToSaveOrUpdate.setUser(user);
        commentToSaveOrUpdate.setMovie(movie);
        commentToSaveOrUpdate.setContent(commentContent);

        commentToSaveOrUpdate.validate();

        commentRepository.save(commentToSaveOrUpdate);
        // Mediator 1 //////////////////////////////////////////////////////////////////////////////////////////////////
        MediatorConfig.MEDIATOR.notify(this, EventType.COMMENT_ADDED);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////observer pattern 1/2 2/2 //////////////////////<ADDED lines>
        List<User> subscribers = userRepository.findAllSubscribedToMovie(movie.getId()); // You’d need to add this method
        MovieCommentNotifier notifier = new MovieCommentNotifier();
        notifier.registerObserver(new EmailNotificationObserver());
        notifier.registerObserver(new SmsNotificationObserver());
        notifier.registerObserver(new InAppNotificationObserver());

        notifier.notifyAll(movie, commentToSaveOrUpdate, subscribers);
        /// /////////////////////////////////////////

        return CommentDtoMapper.map(commentToSaveOrUpdate);
    }

    public void deleteComment(long id, String userEmail) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND));

        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new BadRequestException(NO_PERMISSIONS);
        }

        commentRepository.deleteById(id);
    }

    public List<CommentDto> getCommentsByMovieId(long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException(MOVIE_NOT_FOUND);
        }

        return commentRepository.findByMovieId(movieId)
                .stream()
                .map(comment -> {
                    CommentDto dto = new CommentDto();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setUserId(comment.getUser().getId());
                    return dto;
                })
                .toList();
    }
}