package com.example.autorepairshop.error;

public class CarAlreadyExistException extends RuntimeException {
    public CarAlreadyExistException(String message) {
        super(message);
    }
}
