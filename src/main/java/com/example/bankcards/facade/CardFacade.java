package com.example.bankcards.facade;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardFacade {
    List<CardDto> getAllCards();
    Page<CardDto> getCardsForCurrentUser(CardFilterDto filterDto, Pageable pageable);
    void delete(Long id);
    CardStatus activate(Long cardId);
    CardStatus block(Long cardId);
    CreateCardResponseDto create(CreateCardRequestDto request);
}
