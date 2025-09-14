package com.example.bankcards.dto.response.cardBlockTicket;

import com.example.bankcards.enums.CardBlockTicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response returned after creating a card block ticket")
public class CreateCardBlockTicketResponseDto {

    @Schema(description = "Unique identifier of the card block ticket", example = "101")
    private Long id;

    @Schema(description = "Status of the card block ticket", example = "PENDING")
    private CardBlockTicketStatus status;

    @Schema(description = "ID of the card associated with the block ticket", example = "1234")
    private Long cardId;
}
