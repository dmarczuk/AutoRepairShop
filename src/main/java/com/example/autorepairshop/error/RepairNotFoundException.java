package com.example.autorepairshop.error;

public class RepairNotFoundException extends RuntimeException {
    public RepairNotFoundException(String message) {
        super(message);
    }
}
