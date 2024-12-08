package com.example.warsztat_samochodowy.controller;


import com.example.warsztat_samochodowy.dto.RepairDto;
import com.example.warsztat_samochodowy.dto.CarClientDto;
import com.example.warsztat_samochodowy.dto.UpdateClientRequest;
import com.example.warsztat_samochodowy.dto.TicketDto;
import com.example.warsztat_samochodowy.model.*;
import com.example.warsztat_samochodowy.service.AutoRepairShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AutoRepairShopController {

    private final AutoRepairShopService warsztat_serwis;

    public AutoRepairShopController(AutoRepairShopService warsztat_serwis) {
        this.warsztat_serwis = warsztat_serwis;
    }
    @GetMapping("/klienci")
    public List<Client> Wyswietl_klientow(){
        List<Client> lista_klientow = warsztat_serwis.Podglad_klientow();
        return lista_klientow;
    }

    @PostMapping("/dodaj/klienta")
    public ResponseEntity<Client> Dodaj_klienta(@RequestBody Client klient){
        Client nowy_klient = warsztat_serwis.Dodawanie_klienta(klient);
        return ResponseEntity.ok(nowy_klient);
    }

    @PatchMapping("/modyfikuj/dane/klienta")
    public ResponseEntity<Client> Modyfikuj_dane_klienta(@RequestBody UpdateClientRequest klientRequest){
        Client klient = warsztat_serwis.Modyfikacje_danych_klienta(klientRequest);
        return ResponseEntity.ok(klient);
    }

    @GetMapping("/mechanicy")
    public List<Mechanic> Wyswietl_mechanikow(){
        List<Mechanic> lista_mechanikow = warsztat_serwis.Podglad_mechanikow();
        return lista_mechanikow;
    }

    @PostMapping("/dodaj/mechanika")
    public ResponseEntity<Mechanic> Dodaj_mechanika(@RequestBody Mechanic mechanik){
        Mechanic nowy_mechanik = warsztat_serwis.Dodawanie_mechanika(mechanik);
        return ResponseEntity.ok(nowy_mechanik);
    }

    @PatchMapping("/zwolnij/mechanika")
    public ResponseEntity<Mechanic> Zwolnij_mechanika(@RequestBody Mechanic mechanik){
        warsztat_serwis.Zwolnienie_mechanika(mechanik);
        return ResponseEntity.ok(mechanik);
    }

    @GetMapping("/naprawy")
    public List<Repair> Wyswietl_naprawy(){
        List<Repair> lista_napraw = warsztat_serwis.Podglad_napraw();
        return lista_napraw;
    }

    @PatchMapping("/dodaj/naprawe")
    public ResponseEntity<Repair> Dodaj_naprawe(@RequestBody RepairDto naprawaDto){
        Repair nowa_naprawa = warsztat_serwis.Dodanie_mechanika_do_naprawy(naprawaDto);
        return ResponseEntity.ok(nowa_naprawa);
    }

    @GetMapping("/pojazdy")
    public List<Car> Wyswietl_pojazd(){
        List<Car> lista_pojazdow = warsztat_serwis.Podglad_pojazdow();
        return lista_pojazdow;
    }

    @PostMapping("/dodaj/pojazd")
    public ResponseEntity<Car> Dodaj_pojazd(@RequestBody CarClientDto pojazdKlientDto){
        Car nowy_pojazd = warsztat_serwis.Dodawanie_pojazdu(pojazdKlientDto.getPojazd(), pojazdKlientDto.getTelefonKlienta());
        return ResponseEntity.status(HttpStatus.CREATED).body(nowy_pojazd);
    }

    @PatchMapping("/modyfikuj/dane/pojazdow")
    public ResponseEntity<Car> Modyfikuj_pojazd(@RequestBody Car pojazd){
        Car zmodyfikowanyPojazd = warsztat_serwis.Modyfikacje_danych_pojazdu(pojazd);
        return ResponseEntity.ok(zmodyfikowanyPojazd);
    }

    @PostMapping("/dodaj/nowe/zgloszenie")
    public ResponseEntity<Repair> Nowe_zgloszenie(@RequestBody TicketDto zgloszenie) {
        Repair naprawa = warsztat_serwis.Dodanie_nowego_zgloszenia(zgloszenie.getKlient(), zgloszenie.getPojazd());
        return ResponseEntity.status(HttpStatus.CREATED).body(naprawa);
    }
}
