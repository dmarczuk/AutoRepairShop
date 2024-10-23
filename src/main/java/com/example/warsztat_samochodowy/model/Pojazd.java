package com.example.warsztat_samochodowy.model;

public class Pojazd {

    private int PojazdID;
    private String rejestracja;
    private String marka;
    private String model;
    private int rocznik;
    private int klient; // klucz obcy
    private int VIN; // klucz podstawowy

    public int getPojazdID() {
        return PojazdID;
    }

    public void setPojazdID(int ID) {
        this.PojazdID = ID;
    }

    public String getRejestracja() {
        return rejestracja;
    }

    public void setRejestracja(String rejestracja) {
        this.rejestracja = rejestracja;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRocznik() {
        return rocznik;
    }

    public void setRocznik(int rocznik) {
        this.rocznik = rocznik;
    }

    public int getKlient() {
        return klient;
    }

    public void setKlient(int klient) {
        this.klient = klient;
    }

    public int getVIN() {
        return VIN;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }
}
