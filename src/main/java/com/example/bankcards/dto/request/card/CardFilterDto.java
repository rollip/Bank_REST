package com.example.bankcards.dto.request.card;

import com.example.bankcards.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardFilterDto {

    @Schema(description = "Card number", example = "1234-5678-****-9012")
    private String number;

    @Schema(description = "Expiry date from this date", example = "2025-01-01")
    private LocalDate expiryFrom;

    @Schema(description = "Expiry date up to this date", example = "2025-12-31")
    private LocalDate expiryTo;

    @Schema(description = "Minimum balance", example = "100.50")
    private BigDecimal minBalance;

    @Schema(description = "Maximum balance", example = "5000.00")
    private BigDecimal maxBalance;

    @Schema(description = "Status", example = "ACTIVE")
    private CardStatus status;

}