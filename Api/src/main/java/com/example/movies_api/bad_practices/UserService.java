package com.example.movies_api.bad_practices;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, String> userMap = new HashMap<>();

    public boolean registerUser(User user) {
        if (!userMap.containsKey(user.getUsername())) {
            userMap.put(user.getUsername(), user.getEmail());
            System.out.println("Dodano użytkownika: " + user.getUsername());
            return true;
        }
        System.out.println("Użytkownik już istnieje: " + user.getUsername());
        return false;
    }

    public Map<String, String> getAllUsers() {
        return userMap;
    }

    public void clearAllUsers() {
        userMap.clear();
    }
}
