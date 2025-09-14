package com.example.bankcards.mapper;

import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.entity.CardEntity;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    public TransferResponseDto toResponse(CardEntity cardFrom, String status) {
        return TransferResponseDto.builder()
                .balanceFrom(cardFrom.getBalance())
                .status(status)
                .build();
    }
}
