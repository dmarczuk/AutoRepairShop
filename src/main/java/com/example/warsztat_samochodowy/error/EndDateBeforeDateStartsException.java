package com.example.warsztat_samochodowy.error;

public class EndDateBeforeDateStartsException extends RuntimeException {
    public EndDateBeforeDateStartsException(String message) {
        super(message);
    }
}
