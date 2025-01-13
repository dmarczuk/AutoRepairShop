package com.example.autorepairshop.error;

public class ClientAlreadyExistException extends RuntimeException {
    public ClientAlreadyExistException(String message) {
        super(message);
    }
}
