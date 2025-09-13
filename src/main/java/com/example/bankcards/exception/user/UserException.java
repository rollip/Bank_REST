package com.example.bankcards.exception.user;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
