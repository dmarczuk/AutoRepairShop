package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {


    Optional<Client> findByKlientID(int klientID);
    Optional<Client> findByPhoneNumber(String phoneNumber);

}

