package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.model.Mechanic;

public class RepairMechanicDto {

    private Repair repair;
    private Mechanic mechanic;

    public Repair getRepair() {
        return repair;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }
}
