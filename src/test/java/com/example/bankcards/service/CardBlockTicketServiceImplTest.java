package com.example.bankcards.service;

import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.exception.CardBlockTicketException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardBlockTicketRepository;
import com.example.bankcards.service.impl.CardBlockTicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardBlockTicketServiceImplTest {

    @Mock
    private CardBlockTicketRepository ticketRepository;

    @InjectMocks
    private CardBlockTicketServiceImpl ticketService;

    @Test
    public void create_ShouldCreateTicket_whenNoPendingTicket() {
        Long cardId = 1L;
        CardEntity card = CardEntity.builder().id(cardId).build();

        when(ticketRepository.existsByCardIdAndStatus(cardId, CardBlockTicketStatus.PENDING))
                .thenReturn(false);

        when(ticketRepository.save(any(CardBlockTicketEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        CardBlockTicketEntity result = ticketService.create(card);

        assertEquals(CardBlockTicketStatus.PENDING, result.getStatus());
        assertEquals(card, result.getCard());
        verify(ticketRepository).save(any(CardBlockTicketEntity.class));
    }

    @Test
    public void create_ShouldThrowTicketAlreadyExists_whenPendingTicket() {
        Long cardId = 1L;
        CardEntity card = CardEntity.builder().id(cardId).build();

        when(ticketRepository.existsByCardIdAndStatus(cardId, CardBlockTicketStatus.PENDING))
                .thenReturn(true);

        assertThrows(CardBlockTicketException.TicketAlreadyExists.class, () -> ticketService.create(card));
        verify(ticketRepository, never()).save(any());

    }

    @Test
    public void get_ShouldReturnEntity_WhenFound() {
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity expectedTicket = CardBlockTicketEntity.builder().id(ticketId).card(card).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(expectedTicket));

        CardBlockTicketEntity ticket = ticketService.get(ticketId);

        assertEquals(cardId, ticket.getCard().getId());
        assertEquals(ticketId, ticket.getId());
    }


    @Test
    public void get_ShouldThrowNotFound_WhenNoEntityFound() {
        Long ticketId = 2L;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> ticketService.get(ticketId));
    }

    @Test
    public void getIfPending_ShouldReturnEntity_WhenFoundAndPending() {
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity expectedTicket = CardBlockTicketEntity.builder().id(ticketId).card(card).status(CardBlockTicketStatus.PENDING).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(expectedTicket));

        CardBlockTicketEntity ticket = ticketService.getIfPending(ticketId);

        assertEquals(cardId, ticket.getCard().getId());
        assertEquals(ticketId, ticket.getId());
    }

    @Test
    public void getIfPending_ShouldThrowCardBlockTicketStatusException_WhenFoundAndNotPending() {
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity expectedTicket = CardBlockTicketEntity.builder().id(ticketId).card(card).status(CardBlockTicketStatus.APPROVED).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(expectedTicket));

        assertThrows(CardBlockTicketException.CardBlockTicketStatusException.class, () -> ticketService.getIfPending(ticketId));
    }

    @Test
    public void approve_ShouldReturnApproved_WhenStatusPending(){
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity ticket = CardBlockTicketEntity.builder().id(ticketId).card(card).status(CardBlockTicketStatus.PENDING).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenAnswer(inv -> inv.getArgument(0));

        CardBlockTicketStatus status = ticketService.approve(ticketId);

        assertEquals(CardBlockTicketStatus.APPROVED, status);
    }

    @Test
    public void approve_ShouldThrowCardBlockTicketStatusException_WhenStatusNotPending(){
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity ticket = CardBlockTicketEntity.builder().id(ticketId).card(card).status(CardBlockTicketStatus.APPROVED).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        assertThrows(CardBlockTicketException.CardBlockTicketStatusException.class, () -> ticketService.approve(ticketId));
    }


    @Test
    public void reject_ShouldReturnRejected_WhenStatusPending(){
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity ticket = CardBlockTicketEntity.builder().id(ticketId).card(card).status(CardBlockTicketStatus.PENDING).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenAnswer(inv -> inv.getArgument(0));

        CardBlockTicketStatus status = ticketService.reject(ticketId);

        assertEquals(CardBlockTicketStatus.REJECTED, status);
    }


    @Test
    public void reject_ShouldThrowCardBlockTicketStatusException_WhenStatusPending(){
        Long cardId = 1L;
        Long ticketId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).build();
        CardBlockTicketEntity ticket = CardBlockTicketEntity.builder().id(ticketId).card(card).status(CardBlockTicketStatus.APPROVED).build();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        assertThrows(CardBlockTicketException.CardBlockTicketStatusException.class, () -> ticketService.reject(ticketId));
    }


}
