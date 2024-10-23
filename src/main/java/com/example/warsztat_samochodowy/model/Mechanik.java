package com.example.warsztat_samochodowy.model;

public class Mechanik {

    private int MechanikID;
    private String Imie;
    private String Nazwisko;
    //private int naprawy;

    public int getMechanikID() {
        return MechanikID;
    }

    public void setMechanikID(int mechanikID) {
        this.MechanikID = mechanikID;
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
}
