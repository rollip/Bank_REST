package com.example.bankcards.exception.user;

public class UserAlreadyExistsException extends UserException {

    public UserAlreadyExistsException() {
        super("User already exists");
    }


}
