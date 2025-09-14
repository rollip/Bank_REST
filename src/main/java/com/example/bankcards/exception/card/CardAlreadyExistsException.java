package com.example.bankcards.exception.card;

public class CardAlreadyExistsException extends CardException {

    public CardAlreadyExistsException() {
        super("Card already exists");
    }

}

