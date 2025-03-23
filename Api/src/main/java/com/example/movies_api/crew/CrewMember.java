package com.example.movies_api.crew;

import com.example.movies_api.flyweight.RoleName;
import com.example.movies_api.flyweight.RoleNameConverter;
import com.example.movies_api.model.Movie;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
public class CrewMember extends CrewItem {

    // Flyweight 2 /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //    private String role; // np. "Aktor", "Dźwiękowiec", "Reżyser"
    @Convert(converter = RoleNameConverter.class)
    private RoleName role;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public CrewMember() {}
    public CrewMember(String name, RoleName role) {
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

    public RoleName getRole() {
        return role;
    }
}
