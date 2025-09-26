package com.example.bankcards.service.impl;

import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.exception.CardBlockTicketException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardBlockTicketRepository;
import com.example.bankcards.service.CardBlockTicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CardBlockTicketServiceImpl implements CardBlockTicketService {

    private final CardBlockTicketRepository ticketRepository;

    @Override
    public CardBlockTicketEntity create(CardEntity card) {
        checkNoPendingTicket(card.getId());

        CardBlockTicketEntity ticket = CardBlockTicketEntity.builder()
                .card(card)
                .build();

        return ticketRepository.save(ticket);
    }

    @Override
    public CardBlockTicketEntity get(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public CardBlockTicketEntity getIfPending(Long id) {
        CardBlockTicketEntity ticket = get(id);
        checkIfPending(ticket);
        return ticket;
    }

    @Override
    public CardBlockTicketStatus approve(Long id) {
        CardBlockTicketEntity ticket = get(id);
        ticket.approve();
        return ticketRepository.save(ticket).getStatus();
    }

    @Override
    public CardBlockTicketStatus reject(Long id) {
        CardBlockTicketEntity ticket = get(id);
        ticket.reject();
        return ticketRepository.save(ticket).getStatus();
    }

    private void checkIfPending(CardBlockTicketEntity ticket) {
        if (!ticket.getStatus().equals(CardBlockTicketStatus.PENDING)) {
            throw new CardBlockTicketException.CardBlockTicketStatusException("Card block ticket status is not PENDING");
        }
    }

    private void checkNoPendingTicket(Long cardId) {
        boolean hasPending = ticketRepository.existsByCardIdAndStatus(cardId, CardBlockTicketStatus.PENDING);
        if (hasPending) {
            throw new CardBlockTicketException.TicketAlreadyExists();
        }
    }
}
