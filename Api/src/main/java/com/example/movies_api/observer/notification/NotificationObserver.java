package com.example.movies_api.observer.notification;

import com.example.movies_api.model.User;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Comment;

public interface NotificationObserver {
    void notify(User user, Movie movie, Comment comment);
}
