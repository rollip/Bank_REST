package com.example.bankcards.exception;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }


    public static class UserAlreadyExistsException extends UserException {
        public UserAlreadyExistsException() {
            super("User already exists");
        }
    }

}
