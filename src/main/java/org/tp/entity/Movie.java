package org.tp.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name="movie_actor",
            joinColumns = {@JoinColumn(name="movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private final Set<Actor> actors = new HashSet<Actor>();
    public Set<Actor> getActors() {
        return this.actors;
    }
    public Actor createActor(String name)
    {
        Actor actor = new Actor();
        actor.setName(name);
        return actor;
    }
    public Actor createActor() {
        return new Actor();
    }
    public void removeActor(Actor actor) {
        this.actors.remove(actor);
    }
    public Set<Actor> getMovies() {
        return actors;
    }
    public void addActor(Actor actor)
    {
        this.actors.add(actor);
        // actor.addMovie(this);
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    @Override
    public String toString()
    {
        return this.name;
    }
}
