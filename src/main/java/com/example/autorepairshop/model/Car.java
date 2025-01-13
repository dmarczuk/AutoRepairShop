package com.example.autorepairshop.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Cars")
public class Car {
    private String carId = UUID.randomUUID().toString();

    @Column(unique = true, nullable = false)
    private String vehicleRegistration;
    private String mark;
    private String model;
    private int productionYear;
    @Id
    private String vin;

    @ManyToOne
    @JoinColumn(name = "clientId")
    @JsonBackReference
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vin")
    private List<Repair> repairs;

    public Car(String vehicleRegistration, String mark, String model, int year, String vin) {
        this.vehicleRegistration = vehicleRegistration;
        this.mark = mark;
        this.model = model;
        this.productionYear = year;
        this.vin = vin;
    }

    public Car() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String ID) {
        this.carId = ID;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int year) {
        this.productionYear = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
