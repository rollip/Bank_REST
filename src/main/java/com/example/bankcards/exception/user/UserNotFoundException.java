package com.example.bankcards.exception.user;


public class UserNotFoundException extends UserException {

    public UserNotFoundException() {
        super("User not found");
    }

}
