package com.example.autorepairshop.error;

public class MechanicNotFoundException extends RuntimeException {
    public MechanicNotFoundException(String message) {
        super(message);
    }
}
