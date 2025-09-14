package com.example.bankcards.dto.request.cardBlockTicket;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCardBlockTicketRequestDto {
    @NotNull(message = "cardId shouldn't be null")
    private Long cardId;
}
