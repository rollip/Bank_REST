package com.example.bankcards.exception.cardBlockTicket;

public class CardBlockTicketNotFoundException extends CardBlockTicketException {
    public CardBlockTicketNotFoundException() {
        super("Block ticket not found");
    }
}
