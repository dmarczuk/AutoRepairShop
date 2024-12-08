package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Naprawy")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int naprawaID;
    private Date data_rozpoczecia;
    private Date data_zakonczenia;
    private String stan;
    private String opis_usterki;
    private String protokol_naprawy;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mechanikID")
    private Mechanic mechanik;
    //private int VIN; // klucz obcy
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vin")
    private Car pojazd; // klucz obcy

    public Repair(Car pojazd, Mechanic mechanik) {
        this.pojazd = pojazd;
        this.mechanik = mechanik;
    }

    public Repair(Car pojazd) {
        this.pojazd = pojazd;
    }

    public Repair() {

    }

    public int getNaprawaID() {
        return naprawaID;
    }

    public Date getData_rozpoczecia() {
        return data_rozpoczecia;
    }

    public Date getData_zakonczenia() {
        return data_zakonczenia;
    }

    public String getStan() {
        return stan;
    }

    public String getOpis_usterki() {
        return opis_usterki;
    }

    public String getProtokol_naprawy() {
        return protokol_naprawy;
    }

    public Mechanic getMechanik() {
        return mechanik;
    }

    public Car getPojazd() {
        return pojazd;
    }

    public void setNaprawaID(int naprawaID) {
        this.naprawaID = naprawaID;
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

    public void setVIN(Car pojazd) {
        this.pojazd = pojazd;
    }

    public void setMechanik(Mechanic mechanik) {
        this.mechanik = mechanik;
    }
}
