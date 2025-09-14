package com.example.bankcards.dto.response.card;

import com.example.bankcards.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Response returned after creating a new card")
public class CreateCardResponseDto {

    @Schema(description = "Unique identifier of the created card", example = "123")
    private Long id;

    @Schema(description = "Card number", example = "1234-5678-9012-3456")
    private String number;

    @Schema(description = "Expiry date of the card", example = "2025-12-31")
    private LocalDate expiryDate;

    @Schema(description = "Initial balance of the card", example = "0.00")
    private BigDecimal balance;

    @Schema(description = "Status of the card", example = "ACTIVE")
    private CardStatus status;

    @Schema(description = "ID of the card owner", example = "42")
    private Long ownerId;
}

