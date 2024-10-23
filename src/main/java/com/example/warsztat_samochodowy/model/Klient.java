package com.example.warsztat_samochodowy.model;

public class Klient {

    private int KlientID;
    private String Imie;
    private String Nazwisko;
    private String telefon;
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
