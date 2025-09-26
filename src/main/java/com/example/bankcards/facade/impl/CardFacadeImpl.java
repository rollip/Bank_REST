package com.example.bankcards.facade.impl;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.facade.CardFacade;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardFacadeImpl implements CardFacade {

    private final CardService cardService;
    private final UserService userService;
    private final CardMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public Page<CardDto> getCardsForCurrentUser(CardFilterDto filterDto, Pageable pageable) {
        Long userId = currentUserProvider.getUserId();
        return cardService.getCardsForUser(filterDto, pageable, userId)
                .map(mapper::toDto);
    }

    @Override
    public CreateCardResponseDto create(CreateCardRequestDto request) {
        UserEntity user = userService.findById(request.getOwnerId());
        return mapper.toCreateResponse(cardService.create(user));
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
        return cardService.activate(cardId);
    }

    @Override
    public CardStatus block(Long cardId) {
        return cardService.block(cardId);
    }

    @Override
    public void delete(Long cardId) {
        cardService.delete(cardId);
    }
}


