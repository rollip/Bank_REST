package com.example.bankcards.facade.impl;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.exception.TransferException;
import com.example.bankcards.facade.TransferFacade;
import com.example.bankcards.mapper.TransferMapper;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Transactional
public class TransferFacadeImpl implements TransferFacade {

    private final CardService cardService;
    private final CurrentUserProvider currentUserProvider;
    private final TransferMapper transferMapper;

    @Override
    public TransferResponseDto transferInternal(TransferRequestDto request) {

        Long ownerId = currentUserProvider.getUserId();
        BigDecimal amount = request.getAmount();

        CardEntity cardFrom = cardService.getValidCard(ownerId, request.getFromCardId());
        CardEntity cardTo = cardService.getValidCard(ownerId, request.getToCardId());

        validateDifferentCards(cardFrom, cardTo);

        cardService.withdraw(cardFrom.getId(), amount);
        cardService.deposit(cardTo.getId(), amount);

        return transferMapper.toResponse(cardFrom, "SUCCESS");
    }

    private void validateDifferentCards(CardEntity cardFrom, CardEntity cardTo) {
        if (cardFrom.getId().equals(cardTo.getId())) {
            throw new TransferException.SameCardTransferException();
        }
    }

}
