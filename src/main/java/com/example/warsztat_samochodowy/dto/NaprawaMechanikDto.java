package com.example.warsztat_samochodowy.dto;

import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Mechanik;

public class NaprawaMechanikDto {

    private Naprawa naprawa;
    private Mechanik mechanik;

    public Naprawa getNaprawa() {
        return naprawa;
    }

    public Mechanik getMechanik() {
        return mechanik;
    }
}
