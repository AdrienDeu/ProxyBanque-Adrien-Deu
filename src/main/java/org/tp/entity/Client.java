package org.tp.entity;

import javax.persistence.*;

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

}
