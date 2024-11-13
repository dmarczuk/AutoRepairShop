package com.example.warsztat_samochodowy.error;

public class PojazdAlreadyExistError extends RuntimeException {
    public PojazdAlreadyExistError(String message) {
        super(message);
    }
}
