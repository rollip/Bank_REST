package com.example.bankcards.mapper;

import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.util.CardEncryptor;
import com.example.bankcards.util.CardMasker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardMapper {

    private final CardEncryptor encryptor;
    private final CardMasker masker;

    public CardDto toDto(CardEntity entity) {
        return CardDto.builder()
                .id(entity.getId())
                .number(masker.mask(encryptor.decrypt(entity.getNumber())))
                .expiryDate(entity.getExpiryDate())
                .balance(entity.getBalance())
                .status(entity.getStatus())
                .ownerId(entity.getOwner().getId())
                .build();
    }

    public CreateCardResponseDto toCreateResponse(CardEntity entity) {
        return CreateCardResponseDto.builder()
                .id(entity.getId())
                .number(masker.mask(encryptor.decrypt(entity.getNumber())))
                .expiryDate(entity.getExpiryDate())
                .balance(entity.getBalance())
                .status(entity.getStatus())
                .ownerId(entity.getOwner().getId())
                .build();
    }
}
