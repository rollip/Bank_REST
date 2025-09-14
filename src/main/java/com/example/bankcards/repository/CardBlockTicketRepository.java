package com.example.bankcards.repository;

import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBlockTicketRepository extends JpaRepository<CardBlockTicketEntity, Long> {
    boolean existsByCardIdAndStatus(Long cardId, CardBlockTicketStatus status);
}
