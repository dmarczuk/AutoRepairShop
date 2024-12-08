package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Car;

public class CarClientDto {
    private Car car;
    private String phoneNumber;

    public Car getCar() {
        return car;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
