package com.example.bankcards.facade.impl;

import com.example.bankcards.dto.request.cardBlockTicket.CreateCardBlockTicketRequestDto;
import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.facade.CardBlockTicketFacade;
import com.example.bankcards.mapper.CardBlockTicketMapper;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardBlockTicketService;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class CardBlockTicketFacadeImpl implements CardBlockTicketFacade {

    private final CardBlockTicketService ticketService;
    private final CurrentUserProvider currentUserProvider;
    private final CardBlockTicketMapper mapper;
    private final CardService cardService;

    @Override
    public CreateCardBlockTicketResponseDto create(CreateCardBlockTicketRequestDto requestDto){
        cardService.getValidCard(requestDto.getCardId(), currentUserProvider.getUserId());
        CardBlockTicketEntity entity =  ticketService.create(requestDto.getCardId());
        return mapper.toCreateResponse(entity);
    }

    @Override
    public CardBlockTicketStatus approve(Long id){

        CardBlockTicketEntity ticket = ticketService.getIfPending(id);

        Long cardId = ticketService.get(id).getCard().getId();
        ticketService.approve(id);

        CardEntity card = cardService.getCard(cardId);
        card.block();

        return ticket.getStatus();
    }

    @Override
    public CardBlockTicketStatus reject(Long id){
        return ticketService.reject(id);
    }


}
