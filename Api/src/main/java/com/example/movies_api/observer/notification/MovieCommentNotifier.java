package com.example.movies_api.observer.notification;

import com.example.movies_api.model.User;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Comment;

import java.util.ArrayList;
import java.util.List;

//user observer pattern 1/3 2/3 [previously not existing file]
public class MovieCommentNotifier {
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void registerObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    public void notifyAll(Movie movie, Comment comment, List<User> subscribers) {
        System.out.println("--Demonstarcja wzorca obserwator--");
        for (User user : subscribers) {
            for (NotificationObserver observer : observers) {
                observer.notify(user, movie, comment);
            }
        }
    }
}
