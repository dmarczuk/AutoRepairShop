package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Client;
import com.example.warsztat_samochodowy.model.Car;

public class TicketDto {
    private Client klient;
    private Car pojazd;

    public Client getKlient() {
        return klient;
    }

    public void setKlient(Client klient) {
        this.klient = klient;
    }

    public Car getPojazd() {
        return pojazd;
    }

    public void setPojazd(Car pojazd) {
        this.pojazd = pojazd;
    }
}
