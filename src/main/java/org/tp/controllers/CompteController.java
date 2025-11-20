package org.tp.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tp.entity.CompteBancaire;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @PersistenceContext
    private EntityManager em;

    @GetMapping
    public List<CompteBancaire> lister() {
        return em.createQuery("SELECT c FROM CompteBancaire c", CompteBancaire.class)
               .getResultList();
    }

    @GetMapping("/{id}")
    public CompteBancaire obtenir(@PathVariable Long id) {
        CompteBancaire compte = em.find(CompteBancaire.class, id);
        if (compte == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        return compte;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public CompteBancaire creer(@RequestBody CompteBancaire corps) {
        if (corps.getCreationDate() == null) corps.setCreationDate(new Date());
        em.persist(corps);
        em.flush();
        return corps;
    }

    @PutMapping("/{id}")
    @Transactional
    public CompteBancaire remplacer(@PathVariable Long id, @RequestBody CompteBancaire corps) {
        CompteBancaire existant = em.find(CompteBancaire.class, id);
        if (existant == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        existant.setSolde(corps.getSolde());
        existant.setCreationDate(corps.getCreationDate());
        existant.setEstEpargne(corps.isEstEpargne());
        em.merge(existant);
        return existant;
    }

    @PostMapping("/{id}/deposer")
    @Transactional
    public CompteBancaire deposer(@PathVariable Long id, @RequestBody Map<String, Long> donnees) {
        Long montant = donnees.get("montant");
        if (montant == null || montant <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "montant>0 requis");
        CompteBancaire compte = em.find(CompteBancaire.class, id);
        if (compte == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        Long courant = compte.getSolde() == null ? 0L : compte.getSolde();
        compte.setSolde(courant + montant);
        em.merge(compte);
        return compte;
    }

    @PostMapping("/{id}/retirer")
    @Transactional
    public CompteBancaire retirer(@PathVariable Long id, @RequestBody Map<String, Long> donnees) {
        Long montant = donnees.get("montant");
        if (montant == null || montant <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "montant>0 requis");
        CompteBancaire compte = em.find(CompteBancaire.class, id);
        if (compte == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        long courant = compte.getSolde() == null ? 0L : compte.getSolde();
        if (courant < montant) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fonds insuffisants");
        compte.setSolde(courant - montant);
        em.merge(compte);
        return compte;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void supprimer(@PathVariable Long id) {
        CompteBancaire compte = em.find(CompteBancaire.class, id);
        if (compte == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        em.remove(compte);
    }
}