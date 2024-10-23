package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Pojazdy")
public class Pojazd {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PojazdID;
    private String rejestracja;
    private String marka;
    private String model;
    private int rocznik;
    @Id
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

    public int getVIN() {
        return VIN;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }
}
