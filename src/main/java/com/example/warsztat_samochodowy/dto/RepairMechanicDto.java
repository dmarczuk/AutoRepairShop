package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.model.Mechanic;

public class RepairMechanicDto {

    private Repair naprawa;
    private Mechanic mechanik;

    public Repair getNaprawa() {
        return naprawa;
    }

    public Mechanic getMechanik() {
        return mechanik;
    }
}
