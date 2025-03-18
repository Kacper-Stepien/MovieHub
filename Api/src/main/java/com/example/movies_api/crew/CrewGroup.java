package com.example.movies_api.crew;
import com.example.movies_api.model.Movie;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GROUP")
public class CrewGroup extends CrewItem {

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
}
