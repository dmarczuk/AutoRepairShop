package com.example.autorepairshop.dto;

import com.example.autorepairshop.model.Car;

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
