package com.example.bankcards.service;


import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {
    CardEntity create(UserEntity user);
    CardStatus block(Long id);
    CardStatus activate(Long id);
    BigDecimal deposit(Long id, BigDecimal amount);
    BigDecimal withdraw(Long id, BigDecimal amount);
    void delete(Long id);
    Page<CardEntity> getCardsForUser(CardFilterDto filter, Pageable pageable, Long userId);
    List<CardEntity> getAllCards();
    CardEntity getCard(Long id);
    CardEntity getValidCard(Long ownerId, Long cardId);
}
