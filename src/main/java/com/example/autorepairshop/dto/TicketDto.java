package com.example.autorepairshop.dto;

import com.example.autorepairshop.model.Client;
import com.example.autorepairshop.model.Car;

public record TicketDto (Client client, Car car){
}
