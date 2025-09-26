package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.specification.CardSpecification;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardEncryptor;
import com.example.bankcards.util.CardNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements CardService {

    private final CardEncryptor encryptor;
    private final CardNumberGenerator generator;
    private final CardRepository cardRepository;


    @Value("${application.card.expiry-period-years}")
    private Integer expiryYears;


    @Override
    public CardEntity create(UserEntity user) {
        String encryptedNumber = encryptor.encrypt(generator.generateCardNumber());
        checkDuplicateCardNumber(encryptedNumber);
        CardEntity card = CardEntity.builder()
                .number(encryptedNumber)
                .expiryDate(LocalDate.now().plusYears(expiryYears))
                .owner(user)
                .build();

        return cardRepository.save(card);
    }

    @Override
    public CardStatus block(Long id) {
        CardEntity card = getCard(id);
        card.block();
        return cardRepository.save(card).getStatus();
    }

    @Override
    public CardStatus activate(Long id) {
        CardEntity card = getCard(id);
        card.activate();
        return cardRepository.save(card).getStatus();
    }

    @Override
    public BigDecimal deposit(Long id, BigDecimal amount) {
        CardEntity card = getCard(id);
        card.deposit(amount);
        return cardRepository.save(card).getBalance();
    }

    @Override
    public BigDecimal withdraw(Long id, BigDecimal amount) {
        CardEntity card = getCard(id);
        card.withdraw(amount);
        return cardRepository.save(card).getBalance();
    }


    @Override
    public void delete(Long id) {
        CardEntity card = getCard(id);
        cardRepository.delete(card);
    }

    @Override
    public List<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Page<CardEntity> getCardsForUser(CardFilterDto filterDto, Pageable pageable, Long userId) {
        if (Objects.isNull(filterDto)) {
            return cardRepository.findByOwner_Id(userId, pageable);
        }
        Specification<CardEntity> spec = CardSpecification.ownerById(userId)
                .and(CardSpecification.balanceLTE(filterDto.getMaxBalance()))
                .and(CardSpecification.balanceGTE(filterDto.getMinBalance()))
                .and(CardSpecification.expiryDateGTE(filterDto.getExpiryFrom()))
                .and(CardSpecification.expiryDateLTE(filterDto.getExpiryTo()))
                .and(CardSpecification.numberLike(filterDto.getNumber()));
        return cardRepository.findAll(spec, pageable);
    }

    @Override
    public CardEntity getCard(Long id) {
        return cardRepository.findById(id).orElseThrow(NotFoundException::new);
    }


    @Override
    public CardEntity getValidCard(Long id, Long userId) {
        CardEntity card = getCard(id);
        checkCardOwnership(userId, card);
        checkCardNotBlocked(card);
        return card;
    }

    private void checkCardOwnership(Long userId, CardEntity card) {
        if (!card.getOwner().getId().equals(userId)) {
            throw new CardException.UnauthorizedCardAccessException();
        }
    }

    private void checkCardNotBlocked(CardEntity card) {
        if (card.getStatus().equals(CardStatus.BLOCKED)) {
            throw new CardException.CardStatusException("Card is BLOCKED");
        }
    }

    private void checkDuplicateCardNumber(String encryptedNumber) {
        if (cardRepository.existsByNumber(encryptedNumber)) {
            throw new CardException.CardAlreadyExistsException();
        }
    }


}
