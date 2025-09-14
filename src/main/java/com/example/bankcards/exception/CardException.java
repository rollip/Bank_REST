package com.example.bankcards.exception;

public abstract class CardException extends RuntimeException {

    public CardException(String message) {
        super(message);
    }

    public static class CardFundsException extends CardException {
        public CardFundsException(String message) {
            super(message);
        }
    }

    public static class CardStatusException extends CardException  {
        public CardStatusException(String message) {
            super(message);
        }
    }

    public static class CardAlreadyExistsException extends CardException {
        public CardAlreadyExistsException() {
            super("Card already exists");
        }
    }

    public static class UnauthorizedCardAccessException extends CardException {
        public UnauthorizedCardAccessException() {
            super("User is not owner of this card");
        }
    }

}
