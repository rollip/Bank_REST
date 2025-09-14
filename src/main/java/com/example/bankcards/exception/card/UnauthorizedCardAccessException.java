package com.example.bankcards.exception.card;

public class UnauthorizedCardAccessException extends CardException {
    public UnauthorizedCardAccessException() {
        super("User is not owner of this card");
    }
}
