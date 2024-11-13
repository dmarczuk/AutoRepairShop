package com.example.warsztat_samochodowy.error;

public class KlientAlreadyExistError extends RuntimeException {
    public KlientAlreadyExistError(String message) {
        super(message);
    }
}
