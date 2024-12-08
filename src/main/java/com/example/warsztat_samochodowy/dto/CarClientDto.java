package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Car;

public class CarClientDto {
    private Car pojazd;
    private String telefonKlienta;

    public Car getPojazd() {
        return pojazd;
    }

    public String getTelefonKlienta() {
        return telefonKlienta;
    }
}
