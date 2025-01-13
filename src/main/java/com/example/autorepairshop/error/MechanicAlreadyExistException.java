package com.example.autorepairshop.error;

public class MechanicAlreadyExistException extends RuntimeException {
    public MechanicAlreadyExistException(String message) {
        super(message);
    }
}
