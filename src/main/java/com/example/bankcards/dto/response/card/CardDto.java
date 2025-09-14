package com.example.bankcards.dto.response.card;

import com.example.bankcards.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Represents a bank card with all its details")
public class CardDto {

    @Schema(description = "Unique identifier of the card", example = "123")
    private Long id;

    @Schema(description = "Card number", example = "1234-5678-9012-3456")
    private String number;

    @Schema(description = "Card expiry date", example = "2025-12-31")
    private LocalDate expiryDate;

    @Schema(description = "Current balance on the card", example = "1500.50")
    private BigDecimal balance;

    @Schema(description = "Status of the card", example = "ACTIVE")
    private CardStatus status;

    @Schema(description = "ID of the card owner", example = "42")
    private Long ownerId;
}
