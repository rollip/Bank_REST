package com.example.bankcards.exception;

public abstract class CardBlockTicketException extends RuntimeException {
    public CardBlockTicketException(String message) {
        super(message);
    }

    public static class TicketAlreadyExists extends CardBlockTicketException {
        public TicketAlreadyExists() {
            super("A pending ticket already exists for this card");
        }
    }

    public static class CardBlockTicketStatusException extends CardBlockTicketException {
        public CardBlockTicketStatusException(String message) {
            super(message);
        }
    }
}
