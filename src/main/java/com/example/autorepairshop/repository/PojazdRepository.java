package com.example.autorepairshop.repository;

import com.example.autorepairshop.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PojazdRepository extends JpaRepository<Car, String> {


    Optional<Car> findByVin(String vin);
}
