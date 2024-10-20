package com.example.warsztat_samochodowy.model;

public class Mechanik {

    private int ID;
    private String Imie;
    private String Nazwisko;
    private int naprawy;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public int getNaprawy() {
        return naprawy;
    }

    public void setNaprawy(int naprawy) {
        this.naprawy = naprawy;
    }
}
