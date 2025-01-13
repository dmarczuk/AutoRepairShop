package com.example.autorepairshop.dto;

import com.example.autorepairshop.model.Mechanic;

public class RepairDto {
    private int naprawaId;
    private Mechanic mechanic;


    public RepairDto(int naprawaId) {
        this.naprawaId = naprawaId;
    }

    public RepairDto(int naprawaId, Mechanic mechanic) {
        this.naprawaId = naprawaId;
        this.mechanic = mechanic;
    }

    public int getNaprawaId() {
        return naprawaId;
    }

    public void setNaprawaId(int naprawaId) {
        this.naprawaId = naprawaId;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }
}
