package com.example.warsztat_samochodowy.controller;

import com.example.warsztat_samochodowy.dto.RepairMechanicDto;
import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.service.MechanicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class MechanicController {

    private final MechanicService mechanicService;

    @PatchMapping("/accept/repair")
    public ResponseEntity<Repair> acceptTicket (@RequestBody RepairMechanicDto repairMechanicDto){
        Repair acceptedTicket = mechanicService.acceptTicket(repairMechanicDto.getRepairId(), repairMechanicDto.getMechanicUsername());
        return ResponseEntity.ok(acceptedTicket);
    }

    @PatchMapping("/modify/description")
    public ResponseEntity<Repair> modificationFaultDescription(@RequestBody Repair repair){
        Repair newRepair = mechanicService.modificationFaultDescription(repair);
        return ResponseEntity.ok(newRepair);
    }

    @PatchMapping("/modify/repairStartDate")
    public ResponseEntity<Repair> startRepair(@RequestBody Repair repair){
        Repair newRepair = mechanicService.startRepair(repair);
        return ResponseEntity.ok(newRepair);
    }

    @PatchMapping("/modify/repairEndDate")
    public ResponseEntity<Repair> endDate(@RequestBody Repair repair){
        Repair newRepair = mechanicService.estimatedRepairTime(repair);
        return ResponseEntity.ok(newRepair);
    }


}
