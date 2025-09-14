package com.example.bankcards.mapper;

import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.entity.CardBlockTicketEntity;
import org.springframework.stereotype.Component;

@Component
public class CardBlockTicketMapper {

    public CreateCardBlockTicketResponseDto toCreateResponse(CardBlockTicketEntity entity) {
        return CreateCardBlockTicketResponseDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .cardId(entity.getCard().getId())
                .build();
    }
}
