package com.example.autorepairshop.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Repairs")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int repairId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String state;
    private String description;
    private String repairProtocol;

    private String phoneNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mechanikId")
    private Mechanic mechanic;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vin")
    private Car car;

    public Repair(Car car, Mechanic mechanic) {
        this.car = car;
        this.mechanic = mechanic;
    }

    public Repair(Car car, String phoneNumber) {
        this.car = car;
        this.phoneNumber = phoneNumber;
    }

    public Repair(Car car) {
        this.car = car;
    }

    public Repair() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRepairId() {
        return repairId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public String getRepairProtocol() {
        return repairProtocol;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public Car getCar() {
        return car;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRepairProtocol(String protokol_naprawy) {
        this.repairProtocol = protokol_naprawy;
    }

    public void setMechanic(Mechanic mechanik) {
        this.mechanic = mechanik;
    }
}
