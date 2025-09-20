package com.example.bankcards.service;

import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;

public interface CardBlockTicketService {
    CardBlockTicketEntity create(CardEntity cardEntity);
    CardBlockTicketStatus approve(Long id);
    CardBlockTicketStatus reject(Long id);
    CardBlockTicketEntity get(Long id);
    CardBlockTicketEntity getIfPending(Long id);

}
