package com.example.bankcards.dto.response.card;

import com.example.bankcards.enums.CardStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CardDto {
    private Long id;
    private String number;
    private LocalDate expiryDate;
    private BigDecimal balance;
    private CardStatus status;
    private Long ownerId;
}
