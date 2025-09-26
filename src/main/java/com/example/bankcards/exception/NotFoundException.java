package com.example.bankcards.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Entity not found");
    }
}
