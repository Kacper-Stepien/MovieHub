package com.example.movies_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    // Kompozyt 3 //////////////////////////////////////////////////////////////////////////////////////////////////////
    // Self-join: rodzic
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Genre parent;

    // Self-join: dzieci
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Genre> children = new HashSet<>();

    public Genre(String name) {
        this.name = name;
    }

    public void addChild(Genre child) {
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(Genre child) {
        children.remove(child);
        child.setParent(null);
    }

    public String show(String indent) {
        StringBuilder sb = new StringBuilder(indent + "+ " + name);
        for (Genre child : children) {
            sb.append("\n").append(child.show(indent + "  "));
        }
        return sb.toString();
    }

    public int countSubgenres() {
        int total = children.size();
        for (Genre child : children) {
            total += child.countSubgenres();
        }
        return total;
    }
}