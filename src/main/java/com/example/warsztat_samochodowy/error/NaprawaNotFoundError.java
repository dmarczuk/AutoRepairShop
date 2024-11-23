package com.example.warsztat_samochodowy.error;

public class NaprawaNotFoundError extends RuntimeException {
    public NaprawaNotFoundError(String message) {
        super(message);
    }
}
