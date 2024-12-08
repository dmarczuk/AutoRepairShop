package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.RepairDto;
import com.example.warsztat_samochodowy.dto.UpdateClientRequest;
import com.example.warsztat_samochodowy.error.*;
import com.example.warsztat_samochodowy.model.Client;
import com.example.warsztat_samochodowy.model.Mechanic;
import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.model.Car;
import com.example.warsztat_samochodowy.repository.ClientRepository;
import com.example.warsztat_samochodowy.repository.MechanicRepository;
import com.example.warsztat_samochodowy.repository.RepairRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AutoRepairShopService {

    private ClientRepository klientRepository;
    private MechanicRepository mechanikRepository;
    private RepairRepository naprawaRepository;
    private PojazdRepository pojazdRepository;

    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public AutoRepairShopService(ClientRepository klientRepository,
                                 MechanicRepository mechanikRepository,
                                 RepairRepository naprawaRepository,
                                 PojazdRepository pojazdRepository,
                                 PasswordEncoder passwordEncoder) {
        this.klientRepository = klientRepository;
        this.mechanikRepository = mechanikRepository;
        this.naprawaRepository = naprawaRepository;
        this.pojazdRepository = pojazdRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Client>Podglad_klientow(){
        List<Client> listaKlientow = new ArrayList<>();
        listaKlientow = klientRepository.findAll();
        return listaKlientow;
    }
    @Transactional
    public Client Dodawanie_klienta(Client klient){
        Optional<Client> existingKlient = klientRepository.findByTelefon(klient.getPhoneNumber());
        if (existingKlient.isPresent()) {
            throw new ClientAlreadyExistException("Klient z podanym numerem telefonu już istnieje w bazie");
        }
        return klientRepository.save(klient);
    }
    public Client Modyfikacje_danych_klienta(UpdateClientRequest klient){
        Optional<Client> staryKlient = klientRepository.findByTelefon(klient.getPhoneNumber());
        if (staryKlient.isEmpty()) {
            throw new ClientNotFoundException("Nie znaleziono klienta z podanym numerem telefonu");
        }
        staryKlient.get().setFirstName(klient.getFirstName());
        staryKlient.get().setSecondName(klient.getSecondName());
        staryKlient.get().setEmail(klient.getEmail());
        return klientRepository.save(staryKlient.get());
    }
    public List<Mechanic>Podglad_mechanikow(){
        List<Mechanic> listaMechanikow;
        listaMechanikow = mechanikRepository.findAll();
        return listaMechanikow;
    }
    public Mechanic Dodawanie_mechanika(Mechanic mechanik) {
        String zahaszowaneHaslo = passwordEncoder.encode(mechanik.getPassword());
        Mechanic nowyMechanik = new Mechanic(mechanik.getFirstName(), mechanik.getSecondName(), mechanik.getUsername(), zahaszowaneHaslo);
        Optional<Mechanic> existingMechanik = mechanikRepository.findByLogin(mechanik.getUsername());
        if (existingMechanik.isPresent()) {
            throw new MechanicAlreadyExistException("Mechanik z takim loginem już istnieje w bazie");
        }
        return mechanikRepository.save(nowyMechanik);
    }
    public void Zwolnienie_mechanika(Mechanic mechanik){
        Optional<Mechanic> mechanikZBazy = mechanikRepository.findByLogin(mechanik.getUsername());
        if(mechanikZBazy.isPresent()){
            mechanikZBazy.get().setIfEmployed("NIE");
            mechanikRepository.save(mechanikZBazy.get());
        } else {
            throw new MechanicNotFoundException("Mechanik nie istnieje w bazie");
        }
    }
    public List<Repair>Podglad_napraw(){

        List<Repair> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }
    public Repair Dodawanie_naprawy(Repair naprawa) {
        return naprawaRepository.save(naprawa);
    }

    public Repair Dodanie_mechanika_do_naprawy(RepairDto naprawaDto) {
        //Optional<Pojazd> pojazd = pojazdRepository.findByVin(naprawaDto.getPojazd().getVIN());
        Optional<Mechanic> mechanik = mechanikRepository.findByImieAndNazwisko(naprawaDto.getMechanic().getFirstName(), naprawaDto.getMechanic().getSecondName());
        if (mechanik.isEmpty()) {
            throw new MechanicNotFoundException("Nie znalezniono mechanika z podanym imieniem i nazwiskiem");
        }
        Optional<Repair> naprawa = naprawaRepository.findById(naprawaDto.getNaprawaId());
        if (naprawa.isEmpty()) {
            throw new RepairNotFoundException("Nie znaleziono podanej naprawy. Nie udało się dodać mechanika");
        }
        naprawa.get().setMechanic(mechanik.get());
        return naprawaRepository.save(naprawa.get());
    }
    public List<Car>Podglad_pojazdow(){
        return pojazdRepository.findAll();
    }

    @Transactional
    public Car Dodawanie_pojazdu(Car pojazd, String telefon) {
        Optional<Client> klient = klientRepository.findByTelefon(telefon);
        Optional<Car> existingPojazd = pojazdRepository.findByVin(pojazd.getVIN());
        if (existingPojazd.isPresent()) {
            throw new CarAlreadyExistException("Pojazd z podanym numerem VIN istnieje już w bazie");
        }
        if (klient.isEmpty()) {
            throw new ClientNotFoundException("Klient z podanym telefonem nie istnieje w bazie");
        }
        pojazd.setClient(klient.get());
        return pojazdRepository.save(pojazd);
    }
    public Car Modyfikacje_danych_pojazdu(Car pojazd){
        Optional<Car> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());
        if (staryPojazd.isEmpty()) {
            throw new CarNotFoundException("Pojazd z podanym numerem VIN nie istnieje w bazie");
        }
        staryPojazd.get().setCarId(pojazd.getCarId());
        staryPojazd.get().setMark(pojazd.getMark());
        staryPojazd.get().setModel(pojazd.getModel());
        staryPojazd.get().setVehicleRegistration(pojazd.getVehicleRegistration());
        staryPojazd.get().setYear(pojazd.getYear());
        return pojazdRepository.save(staryPojazd.get());
    }

    @Transactional
    public Repair Dodanie_nowego_zgloszenia(Client klient, Car pojazd) {
        //??? za pierwszym razem nie dziala - if nie wchodza??? debugowac trzeba
        Optional<Client> klientInDatabase = klientRepository.findByTelefon(klient.getPhoneNumber());
        Optional<Car> pojazdInDatabase = pojazdRepository.findByVin(pojazd.getVIN());

        Client savedKlient = klientInDatabase.orElseGet(() -> Dodawanie_klienta(klient));
        Repair nowa_naprawa;
        if (pojazdInDatabase.isEmpty()) {
            Car savedPojazd = Dodawanie_pojazdu(pojazd, klient.getPhoneNumber());// Dodawanie_pojazdu(pojazd, klient);
            nowa_naprawa = new Repair(savedPojazd);
        } else {
            if (!pojazdInDatabase.get().getClient().getPhoneNumber().equals(savedKlient.getPhoneNumber())) {
                throw new ClientAlreadyExistException("Pojazd posiada już właściciela z innym numerem telefonu");
            }
            nowa_naprawa = new Repair(pojazdInDatabase.get());
        }

        return Dodawanie_naprawy(nowa_naprawa);

    }

}
