package org.tp;

import org.tp.entity.Actor;
import org.tp.entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        EntityTransaction txn = em.getTransaction();
        try {
            txn.begin();
            Movie movie = new Movie();
            movie.setName("Fenetre sur cours");
            Actor mainActor = new Actor();
            Actor actor1 = new Actor();
            actor1.setName("Jeu");
            mainActor.setName("hitchcock");
            mainActor.addMovie(movie);
            movie.addActor(mainActor);
            movie.addActor(actor1);
            em.persist(movie);
            Movie movie1 = em.find(Movie.class, 1L);
            for (Actor a : movie1.getActors()) {
                System.out.println(a.getName());
            }
            em.persist(mainActor);

//            Actor actor = new Actor();
//            Movie movie = new Movie();
//            Movie movie2 = new Movie();
//            Movie movie3 = new Movie();
//
//            actor.addMovie(movie);
//            actor.addMovie(movie2);
//            movie2.addActor(actor);
//            em.persist(actor);
//            Actor actorFromDb = em.find(Actor.class,1L);
//            System.out.println(actorFromDb);

            txn.commit();
        } catch (Exception e) {
            if (txn != null) {
                txn.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
