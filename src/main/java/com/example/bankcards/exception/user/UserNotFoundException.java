package com.example.bankcards.exception.user;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' not found", username));
    }

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
