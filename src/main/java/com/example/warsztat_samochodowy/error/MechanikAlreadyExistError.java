package com.example.warsztat_samochodowy.error;

public class MechanikAlreadyExistError extends RuntimeException {
    public MechanikAlreadyExistError(String message) {
        super(message);
    }
}
