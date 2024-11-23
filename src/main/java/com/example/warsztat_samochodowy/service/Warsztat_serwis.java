package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.NaprawaDto;
import com.example.warsztat_samochodowy.dto.UpdateKlientRequest;
import com.example.warsztat_samochodowy.error.*;
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
import org.springframework.dao.DuplicateKeyException;
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
        Optional<Klient> existingKlient = klientRepository.findByTelefon(klient.getTelefon());
        if (existingKlient.isPresent()) {
            throw new KlientAlreadyExistError("Klient z podanym numerem telefonu już istnieje w bazie");
        }
        return klientRepository.save(klient);
    }
    public Klient Modyfikacje_danych_klienta(UpdateKlientRequest klient){
        Optional<Klient> staryKlient = klientRepository.findByTelefon(klient.getTelefon());
        if (staryKlient.isEmpty()) {
            throw new KlientNotFoundError("Nie znaleziono klienta z podanym numerem telefonu");
        }
        staryKlient.get().setImie(klient.getImie());
        staryKlient.get().setNazwisko(klient.getNazwisko());
        staryKlient.get().setEmail(klient.getEmail());
        return klientRepository.save(staryKlient.get());
    }
    public List<Mechanik>Podglad_mechanikow(){
        List<Mechanik> listaMechanikow = new ArrayList<>();
        listaMechanikow = mechanikRepository.findAll();
        return listaMechanikow;
    }
    public Mechanik Dodawanie_mechanika(Mechanik mechanik) {
        Optional<Mechanik> existingMechanik = mechanikRepository.findByImieAndNazwisko(mechanik.getImie(), mechanik.getNazwisko());
        if (existingMechanik.isPresent()) {
            throw new MechanikAlreadyExistError("Mechanik z takim imieniem i nazwiskiem już istnieje w bazie");
        }
        return mechanikRepository.save(mechanik);
    }
    public void Usuniecie_danych_mechanika(Mechanik mechanik){
        Optional<Mechanik> mechanikZBazy = mechanikRepository.findByImieAndNazwisko(mechanik.getImie(), mechanik.getNazwisko());
        if (mechanikZBazy.isEmpty()) {
            throw new MechanikNotFoundError("Nie znalezniono mechanika z podanym imieniem i nazwiskiem");
        }
        mechanikRepository.delete(mechanik);
    }
    public List<Naprawa>Podglad_napraw(){

        List<Naprawa> listaNapraw = new ArrayList<>();
        listaNapraw = naprawaRepository.findAll();
        return listaNapraw;
    }
    public Naprawa Dodawanie_naprawy(Naprawa naprawa) {
        return naprawaRepository.save(naprawa);
    }

    public Naprawa Dodanie_mechanika_do_naprawy(NaprawaDto naprawaDto) {
        //Optional<Pojazd> pojazd = pojazdRepository.findByVin(naprawaDto.getPojazd().getVIN());
        Optional<Mechanik> mechanik = mechanikRepository.findByImieAndNazwisko(naprawaDto.getMechanik().getImie(), naprawaDto.getMechanik().getNazwisko());
        if (mechanik.isEmpty()) {
            throw new MechanikNotFoundError("Nie znalezniono mechanika z podanym imieniem i nazwiskiem");
        }
        Optional<Naprawa> naprawa = naprawaRepository.findById(naprawaDto.getNaprawaID());
        if (naprawa.isEmpty()) {
            throw new NaprawaNotFoundError("Nie znaleziono podanej naprawy. Nie udało się dodać mechanika");
        }
        naprawa.get().setMechanik(mechanik.get());
        return naprawaRepository.save(naprawa.get());
    }
    public List<Pojazd>Podglad_pojazdow(){
        return pojazdRepository.findAll();
    }

    @Transactional
    public Pojazd Dodawanie_pojazdu(Pojazd pojazd, String telefon) {
        Optional<Klient> klient = klientRepository.findByTelefon(telefon);
        Optional<Pojazd> existingPojazd = pojazdRepository.findByVin(pojazd.getVIN());
        if (existingPojazd.isPresent()) {
            throw new PojazdAlreadyExistError("Pojazd z podanym numerem VIN istnieje już w bazie");
        }
        if (klient.isEmpty()) {
            throw new KlientNotFoundError("Klient z podanym telefonem nie istnieje w bazie");
        }
        pojazd.setKlient(klient.get());
        return pojazdRepository.save(pojazd);
    }
    public void Modyfikacje_danych_pojazdu(Pojazd pojazd){
        Optional<Pojazd> staryPojazd = pojazdRepository.findByVin(pojazd.getVIN());
        if (staryPojazd.isEmpty()) {
            throw new PojazdNotFoundError("Pojazd z podanym numerem VIN nie istnieje w bazie");
        }
        staryPojazd.get().setPojazdID(pojazd.getPojazdID());
        staryPojazd.get().setMarka(pojazd.getMarka());
        staryPojazd.get().setModel(pojazd.getModel());
        staryPojazd.get().setRejestracja(pojazd.getRejestracja());
        staryPojazd.get().setRocznik(pojazd.getRocznik());
        pojazdRepository.save(staryPojazd.get());
    }

    @Transactional
    public Naprawa Dodanie_nowego_zgloszenia(Klient klient, Pojazd pojazd) {
        //??? za pierwszym razem nie dziala - if nie wchodza??? debugowac trzeba
        Optional<Klient> klientInDatabase = klientRepository.findByTelefon(klient.getTelefon());
        Optional<Pojazd> pojazdInDatabase = pojazdRepository.findByVin(pojazd.getVIN());

        Klient savedKlient = klientInDatabase.orElseGet(() -> Dodawanie_klienta(klient));
        Naprawa nowa_naprawa;
        if (pojazdInDatabase.isEmpty()) {
            Pojazd savedPojazd = Dodawanie_pojazdu(pojazd, klient.getTelefon());// Dodawanie_pojazdu(pojazd, klient);
            nowa_naprawa = new Naprawa(savedPojazd);
        } else {
            if (pojazdInDatabase.get().getKlient().getTelefon().equals(savedKlient.getTelefon())) {
                throw new KlientAlreadyExistError("Pojazd posiada już właściciela z innym numerem telefonu");
            }
            nowa_naprawa = new Naprawa(pojazdInDatabase.get());
        }

        return Dodawanie_naprawy(nowa_naprawa);

    }

}
