package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Mechanicy")
public class Mechanik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mechanikID;
    @Column(nullable = false)
    private String imie;
    @Column(nullable = false)
    private String nazwisko;

    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String haslo;

    @Column(nullable = false)
    private String czyZatrudniony;
    //private int naprawy;

    @OneToMany(mappedBy = "mechanik", cascade = CascadeType.ALL, orphanRemoval = true)
    // co robi mappedBy, oraz orphanRemoval?
    //@JoinColumn(name = "klientID")
    private List<Naprawa> naprawy;


    public Mechanik(String imie, String nazwisko, String login, String haslo) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.czyZatrudniony = "TAK";
        this.login = login;
        this.haslo = haslo;
    }

    public Mechanik() {
    }

    public int getMechanikID() {
        return mechanikID;
    }

    public void setMechanikID(int mechanikID) {
        this.mechanikID = mechanikID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String password) {
        this.haslo = password;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getCzyZatrudniony() {
        return czyZatrudniony;
    }

    public void setCzyZatrudniony(String czyZatrudniony) {
        this.czyZatrudniony = czyZatrudniony;
    }
}
