package com.example.warsztat_samochodowy.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Klienci")

public class Klient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int KlientID;
    @Column(nullable = false)
    private String Imie;
    @Column(nullable = false)
    private String Nazwisko;
    @Column(nullable = false)
    private String telefon;
    @Column(nullable = false)
    private String email;

    public int getKlientID() {
        return KlientID;
    }

    public String getImie() {
        return Imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setKlientID(int KlientID) {

        this.KlientID = KlientID;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
