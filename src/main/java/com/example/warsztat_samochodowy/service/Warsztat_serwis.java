package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;

import java.util.ArrayList;
import java.util.List;

public class Warsztat_serwis {

    public List<Klient>Podglad_klientow(){
        return new ArrayList<>();
    }
    public void Dodawanie_klienta(Klient klient){
        // dodanie do bazy
    }
    public void Modyfikacje_danych_klienta(Klient klient){
        // modyfikacja do bazy
    }
    public List<Mechanik>Podglad_mechanikow(){
        return new ArrayList<>();
    }
    public void Dodawanie_mechanika(Mechanik mechanik){
        // dodanie do bazy
    }
    public void Usuniecie_danych_mechanika(Mechanik mechanik){
        // usuniecie z bazy
    }
    public List<Naprawa>Podglad_napraw(){
        return new ArrayList<>();
    }
    public void Dodawanie_naprawy(Naprawa naprawa){
        // dodanie do bazy
    }
    public List<Pojazd>Podglad_pojazdow(){
        return new ArrayList<>();
    }
    public void Dodawanie_pojazdu(Pojazd pojazd){
        // dodanie do bazy
    }
    public void Modyfikacje_danych_pojazdu(Pojazd pojazd){
        // modyfikacja do bazy
    }

}
