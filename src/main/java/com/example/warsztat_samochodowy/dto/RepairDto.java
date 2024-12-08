package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Mechanic;

public class RepairDto {
    private int naprawaID;
    private Mechanic mechanik;


    public RepairDto(int naprawaID) {
        this.naprawaID = naprawaID;
    }

    public RepairDto(int naprawaID, Mechanic mechanik) {
        this.naprawaID = naprawaID;
        this.mechanik = mechanik;
    }

    public int getNaprawaID() {
        return naprawaID;
    }

    public void setNaprawaID(int naprawaID) {
        this.naprawaID = naprawaID;
    }

    public Mechanic getMechanik() {
        return mechanik;
    }

    public void setMechanik(Mechanic mechanik) {
        this.mechanik = mechanik;
    }
}
