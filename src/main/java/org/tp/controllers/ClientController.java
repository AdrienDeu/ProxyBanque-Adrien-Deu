package org.tp.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tp.entity.Client;
import org.tp.entity.CompteBancaire;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @PersistenceContext
    private EntityManager em;

    @GetMapping
    public List<Client> lister() {
        return em.createQuery("SELECT c FROM Client c", Client.class)
               .getResultList();
    }

    @GetMapping("/{id}")
    public Client obtenir(@PathVariable Long id) {
        Client client = em.find(Client.class, id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        return client;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Client creer(@RequestBody Client corps) {
        em.persist(corps);
        em.flush();
        return corps;
    }

    @PutMapping("/{id}")
    @Transactional
    public Client remplacer(@PathVariable Long id, @RequestBody Client corps) {
        Client existant = em.find(Client.class, id);
        if (existant == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        existant.setPrenom(corps.getPrenom());
        existant.setNom(corps.getNom());
        existant.setAdresse(corps.getAdresse());
        existant.setTelephone(corps.getTelephone());
        existant.setCodePostal(corps.getCodePostal());
        existant.setVille(corps.getVille());
        em.merge(existant);
        return existant;
    }

    @PostMapping("/{id}/compte-courant")
    @Transactional
    public Client ajouterCompteCourant(@PathVariable Long id, @RequestBody Long numCompte) {
        Client client = em.find(Client.class, id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        CompteBancaire compte = em.find(CompteBancaire.class, numCompte);
        if (compte == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        compte.setEstEpargne(false);
        em.merge(compte);
        client.setCompteCourant(compte);
        em.merge(client);
        return client;
    }

    @PostMapping("/{id}/compte-epargne")
    @Transactional
    public Client ajouterCompteEpargne(@PathVariable Long id, @RequestBody Long numCompte) {
        Client client = em.find(Client.class, id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        CompteBancaire compte = em.find(CompteBancaire.class, numCompte);
        if (compte == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        compte.setEstEpargne(true);
        em.merge(compte);
        client.setCompteEpargne(compte);
        em.merge(client);
        return client;
    }

    @GetMapping("/{id}/compte-courant")
    public CompteBancaire obtenirCompteCourant(@PathVariable Long id) {
        Client client = em.find(Client.class, id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        if (client.getCompteCourant() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte courant inexistant");
        return client.getCompteCourant();
    }

    @GetMapping("/{id}/compte-epargne")
    public CompteBancaire obtenirCompteEpargne(@PathVariable Long id) {
        Client client = em.find(Client.class, id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        if (client.getCompteEpargne() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte épargne inexistant");
        return client.getCompteEpargne();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void supprimer(@PathVariable Long id) {
        Client client = em.find(Client.class, id);
        if (client == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable");
        em.remove(client);
    }
}