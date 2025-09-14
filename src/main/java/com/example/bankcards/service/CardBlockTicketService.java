package com.example.bankcards.service;

import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;

public interface CardBlockTicketService {
    CardBlockTicketEntity create(Long id);
    CardBlockTicketStatus approve(Long id);
    CardBlockTicketStatus reject(Long id);
    CardBlockTicketEntity get(Long id);
    CardBlockTicketEntity getIfPending(Long id);

}
