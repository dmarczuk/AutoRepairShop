package com.example.autorepairshop.error;

public class EndDateBeforeDateStartsException extends RuntimeException {
    public EndDateBeforeDateStartsException(String message) {
        super(message);
    }
}
