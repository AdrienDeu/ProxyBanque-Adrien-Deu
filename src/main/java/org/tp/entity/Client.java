package org.tp.entity;


import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue()
    private Long id;
    private String prenom;
    private String nom;
    private String adresse;
    private String telephone;
    private int codePostal;
    private String ville;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_courant_id")
    private CompteBancaire compteCourant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_epargne_id")
    private CompteBancaire compteEpargne;

    public Client() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public int getCodePostal() {
        return codePostal;
    }
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public CompteBancaire getCompteCourant() {
        return compteCourant;
    }
    public void setCompteCourant(CompteBancaire compteCourant) {
        this.compteCourant = compteCourant;
    }
    public CompteBancaire getCompteEpargne() {
        return compteEpargne;
    }
    public void setCompteEpargne(CompteBancaire compteEpargne) {
        this.compteEpargne = compteEpargne;
    }
}
