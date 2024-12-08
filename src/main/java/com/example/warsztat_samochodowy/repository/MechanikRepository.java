package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Mechanik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MechanikRepository extends JpaRepository<Mechanik, Integer> {


    Optional<Mechanik> findByMechanikID(int mechanikID);

    Optional<Mechanik> findByLogin(String login);

    Optional<Mechanik> findByImieAndNazwisko(String imie, String nazwisko);
}
