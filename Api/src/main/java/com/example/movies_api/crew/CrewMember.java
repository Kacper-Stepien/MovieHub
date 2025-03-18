package com.example.movies_api.crew;

import com.example.movies_api.model.Movie;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
public class CrewMember extends CrewItem {

    private String role; // np. "Aktor", "Dźwiękowiec", "Reżyser"

    public CrewMember() {}
    public CrewMember(String name, String role) {
        super(name);
        this.role = role;
    }

    @Override
    public void addItem(CrewItem item) {
        throw new UnsupportedOperationException("CrewMember to liść - nie może mieć dzieci");
    }

    @Override
    public void removeItem(CrewItem item) {
        throw new UnsupportedOperationException("CrewMember to liść - nie może mieć dzieci");
    }

    @Override
    public String show(String indent) {
        return indent + "- " + getName() + " [" + role + "]";
    }

    @Override
    public int countMembers() {
        return 1;
    }

    public String getRole() {
        return role;
    }
}
