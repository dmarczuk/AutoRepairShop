package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.error.EndDateBeforeDateStartsException;
import com.example.warsztat_samochodowy.error.MechanicNotFoundException;
import com.example.warsztat_samochodowy.error.RepairNotFoundException;
import com.example.warsztat_samochodowy.model.Mechanic;
import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.repository.MechanicRepository;
import com.example.warsztat_samochodowy.repository.RepairRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MechanicService {

    private RepairRepository repairRepository;
    private MechanicRepository mechanicRepository;

    public Repair acceptTicket(int repairId, String mechanicUsername) {
        Optional<Mechanic> mechanicInDatabase = mechanicRepository.findByUsername(mechanicUsername);
        Optional<Repair> repairInDatabase = repairRepository.findByRepairId(repairId);
        if (mechanicInDatabase.isEmpty()) {
            throw new MechanicNotFoundException("Mechanic is not employed in car repair shop");
        }
        if (repairInDatabase.isEmpty()) {
            throw new RepairNotFoundException("The specified repair was not found in the database");
        }
        repairInDatabase.get().setMechanic(mechanicInDatabase.get());
        repairRepository.save(repairInDatabase.get());
        return repairInDatabase.get();

    }

    public Repair modificationFaultDescription (Repair repair){
        Optional<Repair> repairInDatabase = repairRepository.findByRepairId(repair.getRepairId());
        if (repairInDatabase.isEmpty()) {
            throw new RepairNotFoundException("The specified repair was not found in the database");
        }
        repairInDatabase.get().setDescription(repair.getDescription());
        repairInDatabase.get().setState(repair.getState());
        repairInDatabase.get().setRepairProtocol(repair.getRepairProtocol());

        repairRepository.save(repairInDatabase.get());
        return repairInDatabase.get();
    }

    public Repair startRepair(Repair repair){
        Optional<Repair> repairInDatabase = repairRepository.findByRepairId(repair.getRepairId());
        if (repairInDatabase.isEmpty()) {
            throw new RepairNotFoundException("The specified repair was not found in the database");
        }
        repairInDatabase.get().setStartDate(repair.getStartDate());
        repairRepository.save(repairInDatabase.get());
        return repairInDatabase.get();

    }

    public Repair estimatedRepairTime(Repair repair){
        Optional<Repair> repairInDatabase = repairRepository.findByRepairId(repair.getRepairId());
        if (repairInDatabase.isEmpty()) {
            throw new RepairNotFoundException("The specified repair was not found in the database");
        }
        repairInDatabase.get().setEndDate(repair.getEndDate());
        if (repair.getEndDate().before(repairInDatabase.get().getStartDate())) {
            throw new EndDateBeforeDateStartsException("The end date cannot be earlier than the start date");
        }
        repairRepository.save(repairInDatabase.get());
        return repairInDatabase.get();
    }
}
