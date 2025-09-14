package com.example.bankcards.dto.request.cardBlockTicket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCardBlockTicketRequestDto {

    @NotNull(message = "cardId shouldn't be null")
    @Schema(
            description = "ID of the card to be blocked",
            example = "123"
    )
    private Long cardId;
}
