package com.example.warsztat_samochodowy.model;

public class Klient {

    private int ID;
    private String Imie;
    private String Nazwisko;
    private String telefon;
    private String email;
    private int VIN;

    public int getID() {
        return ID;
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

    public int getVIN() {
        return VIN;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }
}
