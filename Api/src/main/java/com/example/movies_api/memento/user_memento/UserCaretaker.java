package com.example.movies_api.memento.user_memento;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class UserCaretaker {

    private final Map<String, UserMemento> userMementos = new HashMap<>();

    public void saveMemento(String email, UserMemento memento) {
        userMementos.put(email, memento);
    }

    public UserMemento getLastMemento(String email) {
        return userMementos.get(email);
    }
}