package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Mechanicy")
public class Mechanik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MechanikID;
    @Column(nullable = false)
    private String Imie;
    @Column(nullable = false)
    private String Nazwisko;
    //private int naprawy;


    public Mechanik(String nazwisko, String imie) {
        this.Nazwisko = nazwisko;
        this.Imie = imie;
    }

    //public Mechanik() {
    //}

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
