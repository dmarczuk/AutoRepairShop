package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.error.EndDateBeforeDateStartsException;
import com.example.warsztat_samochodowy.error.MechanicNotFoundException;
import com.example.warsztat_samochodowy.error.RepairNotFoundException;
import com.example.warsztat_samochodowy.model.Mechanic;
import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.repository.MechanicRepository;
import com.example.warsztat_samochodowy.repository.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MechanicService {

    private RepairRepository naprawaRepository;
    private MechanicRepository mechanikRepository;

    public MechanicService(RepairRepository naprawaRepository, MechanicRepository mechanikRepository, AutoRepairShopService warsztat_serwis) {
        this.naprawaRepository = naprawaRepository;
        this.mechanikRepository = mechanikRepository;
    }

    public Repair Przyjecie_zgloszenia(Repair naprawa, Mechanic mechanik) {
        Optional<Mechanic> mechanikWBazie = mechanikRepository.findByImieAndNazwisko(mechanik.getFirstName(), mechanik.getSecondName());
        Optional<Repair> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getRepairId());
        if (mechanikWBazie.isEmpty()) {
            throw new MechanicNotFoundException("Podany mechanik nie jest zatrudniony w warsztacie");
        }
        if (naprawaWBazie.isEmpty()) {
            throw new RepairNotFoundException("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setMechanic(mechanikWBazie.get());
        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();

    }

    public Repair Modyfikacja_opisu_usterki(Repair naprawa){
        Optional<Repair> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getRepairId());
        if (naprawaWBazie.isEmpty()) {
            throw new RepairNotFoundException("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setDescription(naprawa.getDescription());
        naprawaWBazie.get().setState(naprawa.getState());
        naprawaWBazie.get().setRepairProtocol(naprawa.getRepairProtocol());

        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();
    }

    public Repair Rozpoczecie_naprawy(Repair naprawa){
        Optional<Repair> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getRepairId());
        if (naprawaWBazie.isEmpty()) {
            throw new RepairNotFoundException("Nie znalezniono podanej naprawy w bazie");
        }
//        if (naprawa.getData_rozpoczecia().before(new Date())) {
//            throw new IllegalArgumentException("Data rozpoczęcia nie może być w przeszłości");
//        }
        naprawaWBazie.get().setStartDate(naprawa.getStartDate());
        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();

    }

    public Repair Przewidywany_czas_naprawy(Repair naprawa){
        Optional<Repair> naprawaWBazie = naprawaRepository.findByNaprawaID(naprawa.getRepairId());
        if (naprawaWBazie.isEmpty()) {
            throw new RepairNotFoundException("Nie znalezniono podanej naprawy w bazie");
        }
        naprawaWBazie.get().setEndDate(naprawa.getEndDate());
        if (naprawa.getEndDate().before(naprawaWBazie.get().getStartDate())) {
            throw new EndDateBeforeDateStartsException("Data zakończenia nie może być wcześniejsza niż data rozpoczęcia");
        }
        naprawaRepository.save(naprawaWBazie.get());
        return naprawaWBazie.get();
    }
}
