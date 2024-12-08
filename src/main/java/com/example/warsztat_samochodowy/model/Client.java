package com.example.warsztat_samochodowy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Klienci")

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "klient_id")
    private int klientID;
    @Column(nullable = false)
    private String imie;
    @Column(nullable = false)
    private String nazwisko;
    @Column(nullable = false, unique = true)
    private String telefon;
    @Column(nullable = false)
    private String email;

    //@JsonIgnoreProperties("klient")
    @OneToMany(cascade = CascadeType.ALL) //mappedBy = "klient"
    //@OneToMany(mappedBy = "klient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "klientID")
    @JsonManagedReference
    private List<Car> pojazdy = new ArrayList<>();

    public Client(String imie, String nazwisko, String telefon, String email, List<Car> pojazdy) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.email = email;
    }

    public Client() {
    }

    public List<Car> getPojazdy() {
        return pojazdy;
    }

    public void setPojazdy(List<Car> pojazdy) {
        this.pojazdy = pojazdy;
    }

    public int getKlientID() {
        return klientID;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setKlientID(int KlientID) {

        this.klientID = KlientID;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
