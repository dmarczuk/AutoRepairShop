package com.example.autorepairshop.repository;

import com.example.autorepairshop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {


    Optional<Client> findByClientId(int clientId);
    Optional<Client> findByPhoneNumber(String phoneNumber);

}

