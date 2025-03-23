package com.example.movies_api.crew;
import com.example.movies_api.model.Movie;
import jakarta.persistence.*;

import java.util.*;

@Entity
@DiscriminatorValue("GROUP")
public class CrewGroup extends CrewItem implements Iterable<CrewItem> {

    @OneToMany(mappedBy = "parentGroup", cascade = CascadeType.ALL)
    private List<CrewItem> children = new ArrayList<>();

    public CrewGroup() {}
    public CrewGroup(String name) {
        super(name);
    }

    @Override
    public void addItem(CrewItem item) {
        item.setParentGroup(this);
        children.add(item);
    }

    @Override
    public void removeItem(CrewItem item) {
        children.remove(item);
        item.setParentGroup(null);
    }

    @Override
    public String show(String indent) {
        StringBuilder sb = new StringBuilder(indent + "+ " + getName() + " (group)");
        for (CrewItem child : children) {
            sb.append("\n").append(child.show(indent + "  "));
        }
        return sb.toString();
    }

    @Override
    public int countMembers() {
        return children.stream().mapToInt(CrewItem::countMembers).sum();
    }

    public List<CrewItem> getChildren() {
        return children;
    }

    // Iterator 2 ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<CrewItem> iterator() {
        return new CrewGroupIterator(this);
    }

    private static class CrewGroupIterator implements Iterator<CrewItem> {
        private final Stack<Iterator<CrewItem>> stack = new Stack<>();

        public CrewGroupIterator(CrewGroup root) {
            // Najpierw wrzucamy na stos iterator jednoelementowej listy, zawierającej "root".
            // Dzięki temu sam obiekt CrewGroup "root" też zostanie zwrócony jako pierwszy element iteracji.
            List<CrewItem> initial = new ArrayList<>();
            initial.add(root);
            stack.push(initial.iterator());
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                Iterator<CrewItem> current = stack.peek();
                if (current.hasNext()) {
                    return true;
                } else {
                    stack.pop();
                }
            }
            return false;
        }

        @Override
        public CrewItem next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Pobieramy iterator z wierzchołka stosu
            Iterator<CrewItem> current = stack.peek();
            // Następny element
            CrewItem nextItem = current.next();

            // Jeśli nextItem jest kolejną grupą, wrzucamy iterator jej children na stos
            if (nextItem instanceof CrewGroup group) {
                stack.push(group.getChildren().iterator());
            }
            return nextItem;
        }
    }
}
