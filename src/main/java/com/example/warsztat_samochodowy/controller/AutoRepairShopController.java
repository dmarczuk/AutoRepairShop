package com.example.warsztat_samochodowy.controller;

import com.example.warsztat_samochodowy.dto.RepairDto;
import com.example.warsztat_samochodowy.dto.CarClientDto;
import com.example.warsztat_samochodowy.dto.UpdateClientRequest;
import com.example.warsztat_samochodowy.dto.TicketDto;
import com.example.warsztat_samochodowy.model.*;
import com.example.warsztat_samochodowy.service.AutoRepairShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class AutoRepairShopController {

    private final AutoRepairShopService autoRepairShopService;

    @GetMapping("/clients")
    public List<Client> showClients(){
        return autoRepairShopService.showClients();
    }

    @PostMapping("/add/client")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        Client newCLient = autoRepairShopService.addClient(client);
        return ResponseEntity.ok(newCLient);
    }

    @PatchMapping("/modify/client")
    public ResponseEntity<Client> modifyClientData(@RequestBody UpdateClientRequest clientRequest){
        Client client = autoRepairShopService.clientDataModification(clientRequest);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/mechanics")
    public List<Mechanic> showMechanics(){
        return autoRepairShopService.showMechanics();
    }

    @PostMapping("/add/mechanic")
    public ResponseEntity<Mechanic> addMechanic(@RequestBody Mechanic mechanic){
        Mechanic newMechanic = autoRepairShopService.addMechanic(mechanic);
        return ResponseEntity.ok(newMechanic);
    }

    @PatchMapping("/fire/mechanic")
    public ResponseEntity<Mechanic> fireMechanic(@RequestBody Mechanic mechanic){
        autoRepairShopService.fireMechanic(mechanic);
        return ResponseEntity.ok(mechanic);
    }

    @GetMapping("/repairs")
    public List<Repair> showRepairs(){
        return autoRepairShopService.showRepairs();
    }

    @PatchMapping("/add/repair")
    public ResponseEntity<Repair> addRepair(@RequestBody RepairDto repairDtoDto){
        Repair newRepair = autoRepairShopService.addMechanicToRepair(repairDtoDto);
        return ResponseEntity.ok(newRepair);
    }

    @GetMapping("/cars")
    public List<Car> showCars(){
        return autoRepairShopService.showCars();
    }

    @PostMapping("/add/car")
    public ResponseEntity<Car> addCar(@RequestBody CarClientDto carClientDtoDto){
        Car newCar = autoRepairShopService.addCar(carClientDtoDto.getCar(), carClientDtoDto.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(newCar);
    }

    @PatchMapping("/modify/car")
    public ResponseEntity<Car> modifyCarData(@RequestBody Car car){
        Car modifiedCar = autoRepairShopService.modificationCarData(car);
        return ResponseEntity.ok(modifiedCar);
    }

    @PostMapping("/add/new/ticket")
    public ResponseEntity<Repair> newTicket(@RequestBody TicketDto ticket) {
        Repair repair = autoRepairShopService.addNewTicket(ticket.getClient(), ticket.getCar());
        return ResponseEntity.status(HttpStatus.CREATED).body(repair);
    }
}
