package com.example.bankcards.dto.response.cardBlockTicket;

import com.example.bankcards.enums.CardBlockTicketStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCardBlockTicketResponseDto {
    private Long id;
    private CardBlockTicketStatus status;
    private Long cardId;

}
