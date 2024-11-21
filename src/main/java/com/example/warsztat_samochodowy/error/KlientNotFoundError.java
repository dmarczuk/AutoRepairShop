package com.example.warsztat_samochodowy.error;

public class KlientNotFoundError extends RuntimeException {
    public KlientNotFoundError(String message) {
        super(message);
    }
}
