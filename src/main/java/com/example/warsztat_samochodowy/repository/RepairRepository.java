package com.example.warsztat_samochodowy.repository;

import com.example.warsztat_samochodowy.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Integer> {


    Optional<Repair> findByRepairId(int repairID);

}
