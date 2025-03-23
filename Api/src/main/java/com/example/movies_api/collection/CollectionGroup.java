package com.example.movies_api.collection;

import com.example.movies_api.model.User;
import jakarta.persistence.*;

import java.util.*;

@Entity
@DiscriminatorValue("COLLECTION_GROUP")
public class CollectionGroup extends CollectionItem implements Iterable<CollectionItem> {

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

    // Iterator 3 ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<CollectionItem> iterator() {
        return new CollectionGroupIterator(this);
    }

    private static class CollectionGroupIterator implements Iterator<CollectionItem> {
        private final Stack<Iterator<CollectionItem>> stack = new Stack<>();

        public CollectionGroupIterator(CollectionGroup root) {
            List<CollectionItem> initial = new ArrayList<>();
            initial.add(root);
            stack.push(initial.iterator());
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                Iterator<CollectionItem> current = stack.peek();
                if (current.hasNext()) {
                    return true;
                } else {
                    stack.pop();
                }
            }
            return false;
        }

        @Override
        public CollectionItem next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Iterator<CollectionItem> current = stack.peek();
            CollectionItem nextItem = current.next();

            if (nextItem instanceof CollectionGroup group) {
                stack.push(group.getChildren().iterator());
            }
            return nextItem;
        }
    }
}