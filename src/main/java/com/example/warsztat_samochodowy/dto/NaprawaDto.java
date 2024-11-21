package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Mechanik;

public class NaprawaDto {
    private int naprawaID;
    private Mechanik mechanik;


    public NaprawaDto(int naprawaID) {
        this.naprawaID = naprawaID;
    }

    public NaprawaDto(int naprawaID, Mechanik mechanik) {
        this.naprawaID = naprawaID;
        this.mechanik = mechanik;
    }

    public int getNaprawaID() {
        return naprawaID;
    }

    public void setNaprawaID(int naprawaID) {
        this.naprawaID = naprawaID;
    }

    public Mechanik getMechanik() {
        return mechanik;
    }

    public void setMechanik(Mechanik mechanik) {
        this.mechanik = mechanik;
    }
}
