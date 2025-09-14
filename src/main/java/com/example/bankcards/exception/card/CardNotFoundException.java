package com.example.bankcards.exception.card;


public class CardNotFoundException extends CardException {

    public CardNotFoundException() {
        super("Card not found");
    }

}
