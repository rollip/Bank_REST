package com.example.bankcards.facade;

import com.example.bankcards.dto.request.cardBlockTicket.CreateCardBlockTicketRequestDto;
import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.enums.CardBlockTicketStatus;

public interface CardBlockTicketFacade {
    CreateCardBlockTicketResponseDto create(CreateCardBlockTicketRequestDto requestDto);
    CardBlockTicketStatus approve(Long id);
    CardBlockTicketStatus reject(Long id);
}
