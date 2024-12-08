package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Client;
import com.example.warsztat_samochodowy.model.Car;

public class TicketDto {
    private Client client;
    private Car car;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
