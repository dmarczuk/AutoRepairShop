package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Klient;

import java.util.List;

public class Spis_klientów {
    private List<Klient> klienci;
    public void dodaj_klientów(Klient klient){
        klienci.add(klient);
    }
    public void wyswietl_klientów(){
        for(Klient klient : klienci){
            System.out.println(klient);
        }
    }
}
