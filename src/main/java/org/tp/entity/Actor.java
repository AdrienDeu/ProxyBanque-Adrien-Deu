package org.tp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Actor {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "actors")
    private final Set<Movie> movies = new HashSet<>();
    public void addMovie(Movie movie) {
        movie.addActor(this);
        this.movies.add(movie);
    }
    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.removeActor(this);
    }
    public Set<Movie> getMovies() {
        return this.movies;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
