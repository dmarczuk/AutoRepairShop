package com.example.warsztat_samochodowy.service;

import com.example.warsztat_samochodowy.dto.RepairDto;
import com.example.warsztat_samochodowy.dto.UpdateClientRequest;
import com.example.warsztat_samochodowy.error.*;
import com.example.warsztat_samochodowy.model.Client;
import com.example.warsztat_samochodowy.model.Mechanic;
import com.example.warsztat_samochodowy.model.Repair;
import com.example.warsztat_samochodowy.model.Car;
import com.example.warsztat_samochodowy.repository.ClientRepository;
import com.example.warsztat_samochodowy.repository.MechanicRepository;
import com.example.warsztat_samochodowy.repository.RepairRepository;
import com.example.warsztat_samochodowy.repository.PojazdRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class AutoRepairShopService {

    private ClientRepository clientRepository;
    private MechanicRepository mechanicRepository;
    private RepairRepository repairRepository;
    private PojazdRepository carRepository;
    private PasswordEncoder passwordEncoder;

    public List<Client> showClients(){
        return clientRepository.findAll();
    }
    @Transactional
    public Client addClient(Client client) {
        Optional<Client> clientInDatabase = clientRepository.findByPhoneNumber(client.getPhoneNumber());
        if (clientInDatabase.isPresent()) {
            throw new ClientAlreadyExistException("The client with the given phone number already exists in the database");
        }
        return clientRepository.save(client);
    }
    public Client clientDataModification(UpdateClientRequest client){
        Optional<Client> clientInDatabase = clientRepository.findByPhoneNumber(client.getPhoneNumber());
        if (clientInDatabase.isEmpty()) {
            throw new ClientNotFoundException("Client with the given phone number not found");
        }
        clientInDatabase.get().setFirstName(client.getFirstName());
        clientInDatabase.get().setSecondName(client.getSecondName());
        clientInDatabase.get().setEmail(client.getEmail());
        return clientRepository.save(clientInDatabase.get());
    }
    public List<Mechanic> showMechanics(){
        return mechanicRepository.findAll();
    }
    public Mechanic addMechanic(Mechanic mechanic) {
        String hashedPassword = passwordEncoder.encode(mechanic.getPassword());
        Mechanic newMechanic = new Mechanic(mechanic.getFirstName(), mechanic.getSecondName(), mechanic.getUsername(), hashedPassword);
        Optional<Mechanic> existingMechanik = mechanicRepository.findByUsername(mechanic.getUsername());
        if (existingMechanik.isPresent()) {
            throw new MechanicAlreadyExistException("A mechanic with this login already exists in the database");
        }
        return mechanicRepository.save(newMechanic);
    }
    public void fireMechanic(Mechanic mechanic){
        Optional<Mechanic> mechanicInDatabase = mechanicRepository.findByUsername(mechanic.getUsername());
        if(mechanicInDatabase.isPresent()){
            mechanicInDatabase.get().setIfEmployed("NO");
            mechanicRepository.save(mechanicInDatabase.get());
        } else {
            throw new MechanicNotFoundException("Mechanic with the given username not found");
        }
    }
    public List<Repair> showRepairs(){
        return repairRepository.findAll();
    }
    public Repair addRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    public Repair addMechanicToRepair(RepairDto repairDto) {
        Optional<Mechanic> mechanik = mechanicRepository.findByUsername(repairDto.getMechanic().getUsername());
        if (mechanik.isEmpty()) {
            throw new MechanicNotFoundException("Mechanic with the given username not found");
        }
        Optional<Repair> repair = repairRepository.findById(repairDto.getNaprawaId());
        if (repair.isEmpty()) {
            throw new RepairNotFoundException("Repair not found. Mechanic cannot be added");
        }
        repair.get().setMechanic(mechanik.get());
        return repairRepository.save(repair.get());
    }
    public List<Car> showCars(){
        return carRepository.findAll();
    }

    @Transactional
    public Car addCar(Car car, String phoneNumber) {
        Optional<Client> client = clientRepository.findByPhoneNumber(phoneNumber);
        Optional<Car> carInDatabase = carRepository.findByVin(car.getVin());
        if (carInDatabase.isPresent()) {
            throw new CarAlreadyExistException("Car with the given VIN already exists in the database");
        }
        if (client.isEmpty()) {
            throw new ClientNotFoundException("Client with the given phone number not found");
        }
        car.setClient(client.get());
        return carRepository.save(car);
    }
    public Car modificationCarData(Car car){
        Optional<Car> carInDatabase = carRepository.findByVin(car.getVin());
        if (carInDatabase.isEmpty()) {
            throw new CarNotFoundException("Car with the given VIN not found");
        }
        carInDatabase.get().setCarId(car.getCarId());
        carInDatabase.get().setMark(car.getMark());
        carInDatabase.get().setModel(car.getModel());
        carInDatabase.get().setVehicleRegistration(car.getVehicleRegistration());
        carInDatabase.get().setProductionYear(car.getProductionYear());
        return carRepository.save(carInDatabase.get());
    }

    @Transactional
    public Repair addNewTicket(Client client, Car car) {
        Optional<Client> clientInDatabase = clientRepository.findByPhoneNumber(client.getPhoneNumber());
        Optional<Car> pojazdInDatabase = carRepository.findByVin(car.getVin());

        Client savedClient = clientInDatabase.orElseGet(() -> addClient(client));
        Repair newRepair;
        if (pojazdInDatabase.isEmpty()) {
            Car savedCar = addCar(car, client.getPhoneNumber());
            newRepair = new Repair(savedCar, client.getPhoneNumber());
        } else {
            if (!pojazdInDatabase.get().getClient().getPhoneNumber().equals(savedClient.getPhoneNumber())) {
                throw new ClientAlreadyExistException("Car with the given VIN is already assigned to another client");
            }
            newRepair = new Repair(pojazdInDatabase.get(), client.getPhoneNumber());
        }
        return addRepair(newRepair);
    }
}
