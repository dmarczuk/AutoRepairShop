package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.model.Mechanic;

public class RepairMechanicDto {

    private int repairId;
    private String mechanicUsername;

    public int getRepairId() {
        return repairId;
    }

    public String getMechanicUsername() {
        return mechanicUsername;
    }
}
