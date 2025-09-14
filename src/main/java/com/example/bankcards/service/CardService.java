package com.example.bankcards.service;


import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {
    CardEntity create(Long ownerId);
    CardEntity block(Long id);
    CardEntity activate(Long id);
    void delete(Long id);
    Page<CardEntity> getCardsForUser(CardFilterDto filter, Pageable pageable);
    List<CardEntity> getAllCards();
    CardEntity getCard(Long id);
    CardEntity getValidCard(Long ownerId, Long cardId);
}
