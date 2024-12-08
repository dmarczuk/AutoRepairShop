package com.example.warsztat_samochodowy.controller;

import com.example.warsztat_samochodowy.dto.NaprawaMechanikDto;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.service.Mechanik_serwis;
import com.example.warsztat_samochodowy.service.Warsztat_serwis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class MechanikController {

    private final Mechanik_serwis mechanik_serwis;

    public MechanikController(Mechanik_serwis mechanik_serwis) {
        this.mechanik_serwis = mechanik_serwis;
    }

    @PatchMapping("/przyjecie/naprawy")
    public ResponseEntity<Naprawa> PrzyjecieNaprawy (@RequestBody NaprawaMechanikDto naprawaMechanikDto){
        Naprawa przyjetaNaprawa = mechanik_serwis.Przyjecie_zgloszenia(naprawaMechanikDto.getNaprawa(), naprawaMechanikDto.getMechanik());
        return ResponseEntity.ok(przyjetaNaprawa);
    }

    @PatchMapping("/modyfikuj/opis_usterki")
    public ResponseEntity<Naprawa> Modyfikacja_opisu_usterki(@RequestBody Naprawa naprawa){
        Naprawa nowaNaprawa = mechanik_serwis.Modyfikacja_opisu_usterki(naprawa);
        return ResponseEntity.ok(nowaNaprawa);
    }

    @PatchMapping("/modyfikuj/rozpoczecie_naprawy")
    public ResponseEntity<Naprawa> Rozpoczecie_naprawy(@RequestBody Naprawa naprawa){
        Naprawa nowaNaprawa = mechanik_serwis.Rozpoczecie_naprawy(naprawa);
        return ResponseEntity.ok(nowaNaprawa);
    }

    @PatchMapping("/modyfikuj/zakonczenie_naprawy")
    public ResponseEntity<Naprawa> Zakonczenie_naprawy(@RequestBody Naprawa naprawa){
        Naprawa nowaNaprawa = mechanik_serwis.Przewidywany_czas_naprawy(naprawa);
        return ResponseEntity.ok(nowaNaprawa);
    }


}
