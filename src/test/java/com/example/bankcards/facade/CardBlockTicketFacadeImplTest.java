package com.example.bankcards.facade;

import com.example.bankcards.dto.request.cardBlockTicket.CreateCardBlockTicketRequestDto;
import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.facade.impl.CardBlockTicketFacadeImpl;
import com.example.bankcards.mapper.CardBlockTicketMapper;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardBlockTicketService;
import com.example.bankcards.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardBlockTicketFacadeImplTest {

    @Mock
    private CardBlockTicketService ticketService;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardBlockTicketFacadeImpl facade;

    @BeforeEach
    public void setUp() {
        facade = new CardBlockTicketFacadeImpl(ticketService, currentUserProvider, new CardBlockTicketMapper(), cardService);
    }

    private final Long ticketId = 2L;
    private final Long cardId = 1L;
    private final CardEntity card = CardEntity.builder().id(cardId).status(CardStatus.ACTIVE).build();
    private final CardBlockTicketEntity ticket = CardBlockTicketEntity.builder().id(ticketId).card(card).build();


    @Test
    void create_ShouldReturnValidResponse_WhenCardIsValid() {
        CreateCardBlockTicketRequestDto requestDto = new CreateCardBlockTicketRequestDto(cardId);
        when(cardService.getValidCard(requestDto.getCardId(), currentUserProvider.getUserId())).thenReturn(card);
        when(ticketService.create(card)).thenReturn(ticket);

        CreateCardBlockTicketResponseDto responseDto = facade.create(requestDto);

        assertEquals(ticket.getId(), responseDto.getId());
        assertEquals(requestDto.getCardId(), responseDto.getCardId());
        assertEquals(CardBlockTicketStatus.PENDING, responseDto.getStatus());
    }

    @Test
    void approve_ShouldReturnStatusApproved_WhenAllValid() {
        when(ticketService.getIfPending(ticketId)).thenReturn(ticket);
        when(ticketService.approve(ticketId)).thenReturn(CardBlockTicketStatus.APPROVED);

        CardBlockTicketStatus result = facade.approve(ticketId);

        verify(cardService).block(cardId);
        assertEquals(CardBlockTicketStatus.APPROVED, result);
    }


    @Test
    void reject_ShouldReturnStatusRejected_WhenAllValid() {
        when(ticketService.getIfPending(ticketId)).thenReturn(ticket);
        when(ticketService.reject(ticketId)).thenReturn(CardBlockTicketStatus.REJECTED);

        CardBlockTicketStatus result = facade.reject(ticketId);

        verify(cardService, never()).block(cardId);
        assertEquals(CardBlockTicketStatus.REJECTED, result);
    }


}

