package com.example.movies_api.observer.notification;

import com.example.movies_api.model.User;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Comment;

//user observer pattern 1/3 2/3 [previously not existing file]
public class EmailNotificationObserver implements NotificationObserver {
    @Override
    public void notify(User user, Movie movie, Comment comment) {
        System.out.println("[Email] Sent to " + user.getEmail() + ": New comment on " + movie.getTitle());
    }
}
