package com.example.warsztat_samochodowy.controller;

import com.example.warsztat_samochodowy.dto.RepairMechanicDto;
import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.service.MechanicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MechanicController {

    private final MechanicService mechanik_serwis;

    public MechanicController(MechanicService mechanik_serwis) {
        this.mechanik_serwis = mechanik_serwis;
    }

    @PatchMapping("/przyjecie/naprawy")
    public ResponseEntity<Repair> PrzyjecieNaprawy (@RequestBody RepairMechanicDto naprawaMechanikDto){
        Repair przyjetaNaprawa = mechanik_serwis.Przyjecie_zgloszenia(naprawaMechanikDto.getNaprawa(), naprawaMechanikDto.getMechanik());
        return ResponseEntity.ok(przyjetaNaprawa);
    }

    @PatchMapping("/modyfikuj/opis_usterki")
    public ResponseEntity<Repair> Modyfikacja_opisu_usterki(@RequestBody Repair naprawa){
        Repair nowaNaprawa = mechanik_serwis.Modyfikacja_opisu_usterki(naprawa);
        return ResponseEntity.ok(nowaNaprawa);
    }

    @PatchMapping("/modyfikuj/rozpoczecie_naprawy")
    public ResponseEntity<Repair> Rozpoczecie_naprawy(@RequestBody Repair naprawa){
        Repair nowaNaprawa = mechanik_serwis.Rozpoczecie_naprawy(naprawa);
        return ResponseEntity.ok(nowaNaprawa);
    }

    @PatchMapping("/modyfikuj/zakonczenie_naprawy")
    public ResponseEntity<Repair> Zakonczenie_naprawy(@RequestBody Repair naprawa){
        Repair nowaNaprawa = mechanik_serwis.Przewidywany_czas_naprawy(naprawa);
        return ResponseEntity.ok(nowaNaprawa);
    }


}
