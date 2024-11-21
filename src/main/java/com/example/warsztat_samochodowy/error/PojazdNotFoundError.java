package com.example.warsztat_samochodowy.error;

public class PojazdNotFoundError extends RuntimeException {
    public PojazdNotFoundError(String message) {
        super(message);
    }
}
