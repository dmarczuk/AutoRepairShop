package com.example.autorepairshop.repository;

import com.example.autorepairshop.model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {


    Optional<Mechanic> findByMechanicId(int mechanikID);

    Optional<Mechanic> findByUsername(String username);

    Optional<Mechanic> findByFirstNameAndSecondName(String firstName, String secondName);
}
