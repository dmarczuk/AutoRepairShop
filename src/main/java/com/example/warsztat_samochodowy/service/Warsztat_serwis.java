package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.NaprawaDto;
import com.example.warsztat_samochodowy.model.Klient;
import com.example.warsztat_samochodowy.model.Mechanik;
import com.example.warsztat_samochodowy.model.Naprawa;
import com.example.warsztat_samochodowy.model.Pojazd;
import com.example.warsztat_samochodowy.repository.KlientRepository;
import com.example.warsztat_samochodowy.repository.MechanikRepository;
import com.example.warsztat_samochodowy.repository.NaprawaRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import jakarta.persistence.EntityManager;
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
        Optional<Klient> staryKlient = klientRepository.findByTelefon(klient.getTelefon());
        if(staryKlient.isEmpty()){
//            for (Pojazd pojazd : klient.getPojazdy()) {
//                if (pojazd.getVIN() == null || pojazd.getVIN().isEmpty()) {
//                    throw new IllegalArgumentException("VIN must be provided for each Pojazd.");
//                }
//                pojazd.setKlient(klient);
//                System.out.println("KlientID " + pojazd.getKlient().getKlientID());
//            }
            klient.getPojazdy().forEach(x -> x.setKlient(klient));
            klientRepository.save(klient);
            return klient;
        }
        return staryKlient.get();
    }
    public void Modyfikacje_danych_klienta(Klient klient){
        Optional<Klient> staryKlient = klientRepository.findByKlientID(klient.getKlientID());

        if(staryKlient.isPresent()){
            //staryKlient.get().setKlientID(klient.getKlientID());
            staryKlient.get().setImie(klient.getImie());
            staryKlient.get().setNazwisko(klient.getNazwisko());
            staryKlient.get().setTelefon(klient.getTelefon());
            staryKlient.get().setEmail(klient.getEmail());
        }

        klientRepository.save(staryKlient.get());
    }
    public List<Mechanik>Podglad_mechanikow(){
        List<Mechanik> listaMechanikow = new ArrayList<>();
        listaMechanikow = mechanikRepository.findAll();
        return listaMechanikow;
    }
    public Mechanik Dodawanie_mechanika(Mechanik mechanik){
        mechanikRepository.save(mechanik);
        return mechanik;
    }
    public void Usuniecie_danych_mechanika(Mechanik mechanik){
        // sprawdzic czy mechanik istnieje w bazie
        mechanikRepository.delete(mechanik);
    }
    public List<Naprawa>Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }
    public Naprawa Dodawanie_naprawy(NaprawaDto naprawaDto) throws SQLException {
        Optional<Pojazd> pojazd = pojazdRepository.findByVin(naprawaDto.getPojazd().getVIN());
        Optional<Mechanik> mechanik = mechanikRepository.findByImieAndNazwisko(naprawaDto.getMechanik().getImie(), naprawaDto.getMechanik().getNazwisko());
        if (pojazd.isPresent() && mechanik.isPresent()) {
            Naprawa naprawa = new Naprawa(pojazd.get(), mechanik.get());
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
    public Pojazd Dodawanie_pojazdu(Pojazd pojazd, String telefon) throws SQLException {
        Optional<Pojazd> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());
        if(staryPojazd.isEmpty()) {
            Optional<Klient> wlascicielPojazdu = klientRepository.findByTelefon(telefon);
            if (wlascicielPojazdu.isPresent()) {
                pojazd.setKlient(wlascicielPojazdu.get());
                wlascicielPojazdu.get().getPojazdy().add(pojazd);
//              entityManager.persist(pojazd);
                pojazdRepository.save(pojazd);
                return pojazd;
            } else {
                throw new SQLException("Wlasciciel pojazdu nie istnieje w bazie");            }
        }
        return staryPojazd.get();
    }
    public void Modyfikacje_danych_pojazdu(Pojazd pojazd){
        Optional<Pojazd> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());

        if(staryPojazd.isPresent()){
            staryPojazd.get().setPojazdID(pojazd.getPojazdID());
            staryPojazd.get().setMarka(pojazd.getMarka());
            staryPojazd.get().setModel(pojazd.getModel());
            staryPojazd.get().setRejestracja(pojazd.getRejestracja());
            staryPojazd.get().setRocznik(pojazd.getRocznik());
        }

        pojazdRepository.save(staryPojazd.get());
    }

    public Naprawa Dodanie_nowego_zgloszenia(Klient klient, Pojazd pojazd) throws SQLException {

        Dodawanie_klienta(klient);
        Dodawanie_pojazdu(pojazd, klient.getTelefon());
        Naprawa nowa_naprawa = new Naprawa(pojazd);
       // Dodawanie_naprawy(nowa_naprawa);
        return nowa_naprawa;

    }

}
