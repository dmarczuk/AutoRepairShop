package com.example.warsztat_samochodowy.controller;


import com.example.warsztat_samochodowy.dto.NaprawaDto;
import com.example.warsztat_samochodowy.dto.PojazdKlientDto;
import com.example.warsztat_samochodowy.dto.UpdateKlientRequest;
import com.example.warsztat_samochodowy.dto.ZgloszenieDto;
import com.example.warsztat_samochodowy.model.*;
import com.example.warsztat_samochodowy.service.Warsztat_serwis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarsztatController {

    private final Warsztat_serwis warsztat_serwis;

    public WarsztatController(Warsztat_serwis warsztat_serwis) {
        this.warsztat_serwis = warsztat_serwis;
    }
    @GetMapping("/klienci")
    public List<Klient> Wyswietl_klientow(){
        List<Klient> lista_klientow = warsztat_serwis.Podglad_klientow();
        return lista_klientow;
    }

    @PostMapping("/dodaj/klienta")
    public ResponseEntity<Klient> Dodaj_klienta(@RequestBody Klient klient){
        try {
            Klient nowy_klient = warsztat_serwis.Dodawanie_klienta(klient);
            return ResponseEntity.ok(nowy_klient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PatchMapping("/modyfikuj/dane/klienta")
    public ResponseEntity<Klient> Modyfikuj_dane_klienta(@RequestBody UpdateKlientRequest klientRequest){
        try {
            Klient klient = warsztat_serwis.Modyfikacje_danych_klienta(klientRequest);
            return ResponseEntity.ok(klient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/mechanicy")
    public List<Mechanik> Wyswietl_mechanikow(){
        List<Mechanik> lista_mechanikow = warsztat_serwis.Podglad_mechanikow();
        return lista_mechanikow;
    }

    @PostMapping("/dodaj/mechanika")
    public ResponseEntity<Mechanik> Dodaj_mechanika(@RequestBody Mechanik mechanik){
        Mechanik nowy_mechanik = warsztat_serwis.Dodawanie_mechanika(mechanik);
        return ResponseEntity.ok(nowy_mechanik);
    }

    @DeleteMapping("/usun/mechanika")
    public ResponseEntity<Mechanik> Usun_mechanika(@RequestBody Mechanik mechanik){
        warsztat_serwis.Usuniecie_danych_mechanika(mechanik);
        return ResponseEntity.ok(mechanik);
    }

    @GetMapping("/naprawy")
    public List<Naprawa> Wyswietl_naprawy(){
        List<Naprawa> lista_napraw = warsztat_serwis.Podglad_napraw();
        return lista_napraw;
    }

    @PatchMapping("/dodaj/naprawe")
    public ResponseEntity<Naprawa> Dodaj_naprawe(@RequestBody NaprawaDto naprawaDto){
        try {
            Naprawa nowa_naprawa = warsztat_serwis.Dodanie_mechanika_do_naprawy(naprawaDto);
            return ResponseEntity.ok(nowa_naprawa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/pojazdy")
    public List<Pojazd> Wyswietl_pojazd(){
        List<Pojazd> lista_pojazdow = warsztat_serwis.Podglad_pojazdow();
        return lista_pojazdow;
    }

    @PostMapping("/dodaj/pojazd")
    public ResponseEntity<Pojazd> Dodaj_pojazd(@RequestBody PojazdKlientDto pojazdKlientDto){
        try {
            Pojazd nowy_pojazd = warsztat_serwis.Dodawanie_pojazdu(pojazdKlientDto.getPojazd(), pojazdKlientDto.getTelefonKlienta());
            return ResponseEntity.status(HttpStatus.CREATED).body(nowy_pojazd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/modyfikuj/dane/pojazdow")
    public ResponseEntity<Pojazd> Modyfikuj_pojazd(@RequestBody Pojazd pojazd){
        warsztat_serwis.Modyfikacje_danych_pojazdu(pojazd);
        return ResponseEntity.ok(pojazd);
    }

    @PostMapping("/dodaj/nowe/zgloszenie")
    public ResponseEntity<Naprawa> Nowe_zgloszenie(@RequestBody ZgloszenieDto zgloszenie) {
        try {
            Naprawa naprawa = warsztat_serwis.Dodanie_nowego_zgloszenia(zgloszenie.getKlient(), zgloszenie.getPojazd());
            return ResponseEntity.status(HttpStatus.CREATED).body(naprawa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
