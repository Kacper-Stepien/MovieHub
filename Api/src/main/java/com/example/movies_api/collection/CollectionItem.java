package com.example.movies_api.collection;

import com.example.movies_api.model.User;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "collection_type")
@Table(name = "collections")
public abstract class CollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    protected CollectionItem() {
    }

    protected CollectionItem(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public User getOwner() {return owner; }

    public abstract void addItem(CollectionItem item);
    public abstract void removeItem(CollectionItem item);
    public abstract String show(String indent);

    public abstract int countMovies();
}