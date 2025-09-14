package com.example.bankcards.dto.request.card;

import com.example.bankcards.enums.CardStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardFilterDto {
    private String number;
    private LocalDate expiryFrom;
    private LocalDate expiryTo;
    private BigDecimal minBalance;
    private BigDecimal maxBalance;
    private CardStatus status;
    private Long ownerId;
}
