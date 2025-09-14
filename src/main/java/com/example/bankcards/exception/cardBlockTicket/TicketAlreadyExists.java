package com.example.bankcards.exception.cardBlockTicket;

public class TicketAlreadyExists extends CardBlockTicketException {
    public TicketAlreadyExists() {
        super("A pending ticket already exists for this card");
    }
}
