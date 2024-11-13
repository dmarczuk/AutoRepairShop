package com.example.warsztat_samochodowy.dto;

public class NaprawaDto {
    private PojazdDto pojazd;
    private MechanikDto mechanik;

    public PojazdDto getPojazd() {
        return pojazd;
    }

    public void setPojazd(PojazdDto pojazd) {
        this.pojazd = pojazd;
    }

    public MechanikDto getMechanik() {
        return mechanik;
    }

    public void setMechanik(MechanikDto mechanik) {
        this.mechanik = mechanik;
    }
}
