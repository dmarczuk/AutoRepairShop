package com.example.warsztat_samochodowy.error;

public class MechanicAlreadyExistException extends RuntimeException {
    public MechanicAlreadyExistException(String message) {
        super(message);
    }
}
