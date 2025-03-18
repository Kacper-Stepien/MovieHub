package com.example.movies_api.collection;

import com.example.movies_api.model.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("COLLECTION_GROUP")
public class CollectionGroup extends CollectionItem {

    @OneToMany(cascade = CascadeType.ALL)
    private List<CollectionItem> children = new ArrayList<>();

    public CollectionGroup() {
    }

    public CollectionGroup(String name, User owner) {
        super(name, owner);
    }

    @Override
    public void addItem(CollectionItem item) {
        children.add(item);
    }

    @Override
    public void removeItem(CollectionItem item) {
        children.remove(item);
    }

    @Override
    public String show(String indent) {
        StringBuilder sb = new StringBuilder(indent + "+ " + getName() + " (folder)");
        for (CollectionItem child : children) {
            sb.append("\n").append(child.show(indent + "  "));
        }
        return sb.toString();
    }

    @Override
    public int countMovies() {
        return children.stream().mapToInt(CollectionItem::countMovies).sum();
    }

    public List<CollectionItem> getChildren() {
        return children;
    }
}