package com.example.warsztat_samochodowy.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Pojazdy")
public class Car {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pojazdID = UUID.randomUUID().toString();

    @Column(unique = true, nullable = false)
    private String rejestracja;
    private String marka;
    private String model;
    private int rocznik;
    @Id
    //@JsonProperty("vin")
    private String vin; // klucz podstawowy

//    @JsonIgnoreProperties("pojazdy")
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "klient_klientID", insertable = false, updatable = false)
//
    @ManyToOne
    @JoinColumn(name = "klientid")
    @JsonBackReference
    private Client klient;
    //private int klientID;

    @OneToMany(cascade = CascadeType.ALL)
    //@OneToMany(mappedBy = "pojazd", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vin")
    private List<Repair> naprawy;



    public Car(String rejestracja, String marka, String model, int rocznik, String vin) {
        this.rejestracja = rejestracja;
        this.marka = marka;
        this.model = model;
        this.rocznik = rocznik;
        this.vin = vin;
    }

    public Car() {
    }

    public Client getKlient() {
        return klient;
    }

    public void setKlient(Client klient) {
        this.klient = klient;
    }

    public String getPojazdID() {
        return pojazdID;
    }

    public void setPojazdID(String ID) {
        this.pojazdID = ID;
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

    public String getVIN() {
        return vin;
    }

    public void setVIN(String VIN) {
        this.vin = VIN;
    }
}
