package com.example.movies_api.model;

import com.example.movies_api.open_close.GenreFormatter;
import com.example.movies_api.open_close.SimpleGenreFormatter;
import com.example.movies_api.visitor.GenreVisitor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Iterable<Genre> {
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

    // Abstract 1 //////////////////////////////////////////////////////////////////////////////////////////////////////
//    public String show(String indent) {
//        StringBuilder sb = new StringBuilder(indent + "+ " + name);
//        for (Genre child : children) {
//            sb.append("\n").append(child.show(indent + "  "));
//        }
//        return sb.toString();


    public String show(String indent) {
        GenreFormatter formatter = new SimpleGenreFormatter();
        String formatted = formatter.format(this);
        StringBuilder sb = new StringBuilder(indent + formatted);
        for (Genre child : children) {
            sb.append("\n").append(child.show(indent + "  "));
        }
        return sb.toString();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int countSubgenres() {
        int total = children.size();
        for (Genre child : children) {
            total += child.countSubgenres();
        }
        return total;
    }

    // Visitor 3 ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public void accept(GenreVisitor visitor) {
        accept(visitor, 0);
    }

    private void accept(GenreVisitor visitor, int depth) {
        visitor.visit(this, depth);
        for (Genre child : children) {
            child.accept(visitor, depth + 1);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Iterator 1 //////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<Genre> iterator() {
        return new GenreIterator(this);
    }

    private static class GenreIterator implements Iterator<Genre> {
        private final Stack<Iterator<Genre>> stack = new Stack<>();

        public GenreIterator(Genre root) {
            // Rozpoczynamy iterację od root-a – tworzymy listę zawierającą tylko root
            List<Genre> initial = new ArrayList<>();
            initial.add(root);
            stack.push(initial.iterator());
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                Iterator<Genre> current = stack.peek();
                if (current.hasNext()) {
                    return true;
                } else {
                    stack.pop();
                }
            }
            return false;
        }

        @Override
        public Genre next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Iterator<Genre> current = stack.peek();
            Genre nextItem = current.next();
            if (nextItem.getChildren() != null && !nextItem.getChildren().isEmpty()) {
                // Dodajemy iterator dzieci do stosu
                stack.push(nextItem.getChildren().iterator());
            }
            return nextItem;
        }
    }
}