package com.example.warsztat_samochodowy.error;

public class RepairNotFoundException extends RuntimeException {
    public RepairNotFoundException(String message) {
        super(message);
    }
}
