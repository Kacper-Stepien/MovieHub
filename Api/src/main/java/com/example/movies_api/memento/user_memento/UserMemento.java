package com.example.movies_api.memento.user_memento;


//3/3 memento pattern - for rolling back user detail from before modification
//[previously] that file did not exist

//wzorzec pamiątka do zapisywania stanu obiektu użytkownika na wypadek gdyby chciał cofnąć wprowadzone zmiany:
public class UserMemento {
    private final String firstName;
    private final String lastName;
    private final String email;

    public UserMemento(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}