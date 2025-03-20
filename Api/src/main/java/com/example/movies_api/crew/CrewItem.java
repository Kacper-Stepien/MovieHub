package com.example.movies_api.crew;
import com.example.movies_api.model.Movie;
import jakarta.persistence.*;

// Kompozyt 2 //////////////////////////////////////////////////////////////////////////////////////////////////////////
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "crew_type")
@Table(name = "crew")
public abstract class CrewItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Relacja do rodzica w drzewie – null dla root
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CrewGroup parentGroup;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = true)
    private Movie movie;

    protected CrewItem() {}

    protected CrewItem(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public CrewGroup getParentGroup() {
        return parentGroup;
    }
    public void setParentGroup(CrewGroup parent) {
        this.parentGroup = parent;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public abstract void addItem(CrewItem item);
    public abstract void removeItem(CrewItem item);
    public abstract String show(String indent);
    public abstract int countMembers();
}
