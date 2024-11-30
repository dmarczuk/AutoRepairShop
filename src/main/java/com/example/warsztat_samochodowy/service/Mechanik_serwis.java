package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.error.MechanikNotFoundError;
import com.example.warsztat_samochodowy.error.NaprawaNotFoundError;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class Mechanik_serwis {

    private NaprawaRepository naprawaRepository;
    private MechanikRepository mechanikRepository;

    public Mechanik_serwis(NaprawaRepository naprawaRepository, MechanikRepository mechanikRepository, Warsztat_serwis warsztat_serwis) {
        this.naprawaRepository = naprawaRepository;
        this.mechanikRepository = mechanikRepository;
    }

    public Naprawa Przyjecie_zgloszenia(Naprawa naprawa, Mechanik mechanik) {
        Optional<Mechanik> mechanikWBazie = mechanikRepository.findByImieAndNazwisko(mechanik.getImie(), mechanik.getNazwisko());
        Optional<Naprawa> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getNaprawaID());
        if (mechanikWBazie.isEmpty()) {
            throw new MechanikNotFoundError("Podany mechanik nie jest zatrudniony w warsztacie");
        }
        if (naprawaWBazie.isEmpty()) {
            throw new NaprawaNotFoundError("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setMechanik(mechanikWBazie.get());
        naprawaRepository.save(naprawaWBazie.get());
        return naprawa;

    }

    public Naprawa Modyfikacja_opisu_usterki(Naprawa naprawa){
        Optional<Naprawa> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getNaprawaID());
        if (naprawaWBazie.isEmpty()) {
            throw new NaprawaNotFoundError("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setOpis_usterki(naprawa.getOpis_usterki());
        naprawaWBazie.get().setStan(naprawa.getStan());
        naprawaWBazie.get().setProtokol_naprawy(naprawa.getProtokol_naprawy());

        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();
    }

    public Naprawa Rozpoczecie_naprawy(Naprawa naprawa){
        Optional<Naprawa> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getNaprawaID());
        if (naprawaWBazie.isEmpty()) {
            throw new NaprawaNotFoundError("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setData_rozpoczecia(naprawa.getData_rozpoczecia());

        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();

    }

    public Naprawa Przewidywany_czas_naprawy(Naprawa naprawa){
        Optional<Naprawa> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getNaprawaID());
        if (naprawaWBazie.isEmpty()) {
            throw new NaprawaNotFoundError("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setData_zakonczenia(naprawa.getData_zakonczenia());

        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();
    }
}
