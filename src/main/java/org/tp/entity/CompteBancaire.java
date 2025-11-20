package org.tp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CompteBancaire {
    @Id
    private Long numCompte;
    private Long solde;
    private Date creationDate;
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
}
