package com.example.bankcards.facade.impl;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.facade.CardFacade;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardFacadeImpl implements CardFacade {

    private final CardService cardService;
    private final CardMapper mapper;

    @Override
    public Page<CardDto> getCardsForUser(CardFilterDto filterDto, Pageable pageable) {
        return cardService.getCardsForUser(filterDto, pageable)
                .map(mapper::toDto);
    }

    @Override
    public CreateCardResponseDto create(CreateCardRequestDto request) {
        return mapper.toCreateResponse(cardService.create(request.getOwnerId()));
    }

    @Override
    public List<CardDto> getAllCards() {
        return cardService.getAllCards()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CardStatus activate(Long cardId) {
        return cardService.activate(cardId).getStatus();
    }

    @Override
    public CardStatus block(Long cardId) {
        return cardService.block(cardId).getStatus();
    }

    @Override
    public void delete(Long cardId) {cardService.delete(cardId);}
}


