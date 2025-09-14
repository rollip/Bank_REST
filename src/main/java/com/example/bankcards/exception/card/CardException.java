package com.example.bankcards.exception.card;

public abstract class CardException extends RuntimeException {

    CardException(String message) {
        super(message);
    }

}
