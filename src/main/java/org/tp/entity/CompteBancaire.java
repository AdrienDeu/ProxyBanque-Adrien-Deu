package org.tp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class CompteBancaire {
    @Id
    private Long numCompte;
    private Long solde;
    private Date creationDate;
    private boolean estEpargne;
    public CompteBancaire() {
    }
    public Long getNumCompte() {
        return numCompte;
    }
    public void setNumCompte(Long numCompte) {
        this.numCompte = numCompte;
    }
    public Long getSolde() {
        return solde;
    }
    public void setSolde(Long solde) {
        this.solde = solde;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public boolean isEstEpargne() {
        return estEpargne;
    }
    public void setEstEpargne(boolean estEpargne) {
        this.estEpargne = estEpargne;
    }
}