package com.example.warsztat_samochodowy.error;

public class MechanikNotFoundError extends RuntimeException {
    public MechanikNotFoundError(String message) {
        super(message);
    }
}
