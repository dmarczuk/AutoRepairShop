package com.example.warsztat_samochodowy.model;

import java.util.Date;

public class Naprawa {

    private int NaprawaID;
    private Date data_rozpoczecia;
    private Date data_zakonczenia;
    private String stan;
    private String opis_usterki;
    private String protokol_naprawy;
    private int VIN; // klucz obcy
    private int mechanik; // klucz obcy

    public void setNaprawaID(int naprawaID) {
        this.NaprawaID = naprawaID;
    }

    public void setData_rozpoczecia(Date data_rozpoczecia) {
        this.data_rozpoczecia = data_rozpoczecia;
    }

    public void setData_zakonczenia(Date data_zakonczenia) {
        this.data_zakonczenia = data_zakonczenia;
    }

    public void setOpis_usterki(String opis_usterki) {
        this.opis_usterki = opis_usterki;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public void setProtokol_naprawy(String protokol_naprawy) {
        this.protokol_naprawy = protokol_naprawy;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }

    public void setMechanik(int mechanik) {
        this.mechanik = mechanik;
    }
}
