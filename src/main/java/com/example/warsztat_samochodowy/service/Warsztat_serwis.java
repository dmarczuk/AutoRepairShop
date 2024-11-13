package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.UpdateKlientRequest;
import com.example.warsztat_samochodowy.error.KlientAlreadyExistError;
import com.example.warsztat_samochodowy.error.MechanikAlreadyExistError;
import com.example.warsztat_samochodowy.error.PojazdAlreadyExistError;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class Warsztat_serwis {

    private KlientRepository klientRepository;
    private MechanikRepository mechanikRepository;
    private NaprawaRepository naprawaRepository;
    private PojazdRepository pojazdRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Warsztat_serwis(KlientRepository klientRepository,
                           MechanikRepository mechanikRepository,
                           NaprawaRepository naprawaRepository,
                           PojazdRepository pojazdRepository) {
        this.klientRepository = klientRepository;
        this.mechanikRepository = mechanikRepository;
        this.naprawaRepository = naprawaRepository;
        this.pojazdRepository = pojazdRepository;
    }

    public List<Klient>Podglad_klientow(){
        List<Klient> listaKlientow = new ArrayList<>();
        listaKlientow = klientRepository.findAll();
        return listaKlientow;
    }
    @Transactional
    public Klient Dodawanie_klienta(Klient klient){
        try {
            return klientRepository.save(klient);
        } catch (Exception e) {
            throw new KlientAlreadyExistError("Klient z podanym numerem telefonu już istnieje w bazie");
        }
    }
    public Klient Modyfikacje_danych_klienta(UpdateKlientRequest klient){
        Optional<Klient> staryKlient = klientRepository.findByTelefon(klient.getTelefon());

        if(staryKlient.isPresent()){
            //staryKlient.get().setKlientID(klient.getKlientID());
            staryKlient.get().setImie(klient.getImie());
            staryKlient.get().setNazwisko(klient.getNazwisko());
            staryKlient.get().setEmail(klient.getEmail());
            return klientRepository.save(staryKlient.get());
        } else {
            throw new EntityNotFoundException("Klient nie istnieje w bazie");
        }
    }
    public List<Mechanik>Podglad_mechanikow(){
        List<Mechanik> listaMechanikow = new ArrayList<>();
        listaMechanikow = mechanikRepository.findAll();
        return listaMechanikow;
    }
    public Mechanik Dodawanie_mechanika(Mechanik mechanik){
        try {
            return mechanikRepository.save(mechanik);
        } catch (Exception e) {
            throw new MechanikAlreadyExistError("Mechanik z takim imieniem i nazwiskiem już istnieje w bazie");
        }
    }
    public void Usuniecie_danych_mechanika(Mechanik mechanik){
        // sprawdzic czy mechanik istnieje w bazie
        Optional<Mechanik> mechanikZBazy = mechanikRepository.findByImieAndNazwisko(mechanik.getImie(), mechanik.getNazwisko());
        mechanikZBazy.ifPresent(mechanikRepository::delete);
    }
    public List<Naprawa>Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }
    public Naprawa Dodawanie_naprawy(Naprawa naprawa) throws SQLException {
        Optional<Pojazd> pojazd = pojazdRepository.findByVin(naprawa.getPojazd().getVIN());
        Optional<Mechanik> mechanik = mechanikRepository.findByImieAndNazwisko(naprawa.getMechanik().getImie(), naprawa.getMechanik().getNazwisko());
        if (pojazd.isPresent() && mechanik.isPresent()) {
           return naprawaRepository.save(naprawa);
        } else {
            throw new SQLException("Nie udalo sie utworzyc naprawy");
        }
    }
    public List<Pojazd>Podglad_pojazdow(){

        List<Pojazd> listaPojazdow = new ArrayList<>();
        listaPojazdow = pojazdRepository.findAll();
        return listaPojazdow;
    }

    @Transactional
    public Pojazd Dodawanie_pojazdu(Pojazd pojazd, String telefon) {
        Optional<Klient> klient = klientRepository.findByTelefon(telefon);
        try {
            if (klient.isPresent()) {
                pojazd.setKlient(klient.get());
                return pojazdRepository.save(pojazd);
            } else {
                throw new EntityNotFoundException("Klient z podanym telefonem nie istnieje w bazie");
            }
        } catch (Exception e) {
            throw new PojazdAlreadyExistError("Pojazd z podanym numerem VIN istnieje już w bazie");
        }
    }
    public void Modyfikacje_danych_pojazdu(Pojazd pojazd){
        Optional<Pojazd> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());

        if(staryPojazd.isPresent()){
            staryPojazd.get().setPojazdID(pojazd.getPojazdID());
            staryPojazd.get().setMarka(pojazd.getMarka());
            staryPojazd.get().setModel(pojazd.getModel());
            staryPojazd.get().setRejestracja(pojazd.getRejestracja());
            staryPojazd.get().setRocznik(pojazd.getRocznik());
            pojazdRepository.save(staryPojazd.get());
        } else {
            throw new EntityNotFoundException("Pojazd nie istnieje w bazie");
        }
    }

    public Naprawa Dodanie_nowego_zgloszenia(Klient klient, Pojazd pojazd) throws SQLException {
        Optional<Klient> klientInDatabase = klientRepository.findByTelefon(klient.getTelefon());
        Optional<Pojazd> pojazdInDatabase = pojazdRepository.findByVin(pojazd.getVIN());
        if (klientInDatabase.isEmpty()) {
            Dodawanie_klienta(klient);
        }
        Naprawa nowa_naprawa;
        if (pojazdInDatabase.isEmpty()) {
            Dodawanie_pojazdu(pojazd, klient.getTelefon());  // Dodawanie_pojazdu(pojazd, klient);
            nowa_naprawa = new Naprawa(pojazd);
        } else {
            nowa_naprawa = new Naprawa(pojazdInDatabase.get());
        }

        return Dodawanie_naprawy(nowa_naprawa);

    }

}
