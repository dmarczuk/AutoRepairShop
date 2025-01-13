package com.example.autorepairshop.dto;

import com.example.autorepairshop.model.Client;
import com.example.autorepairshop.model.Car;

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
