package com.example.bankcards.entity;

import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.exception.CardBlockTicketException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="card_block_ticket")
public class CardBlockTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CardBlockTicketStatus status = CardBlockTicketStatus.PENDING;

    @OneToOne
    @NotNull
    private CardEntity card;

    public void approve() {
        checkStatusPending();
        this.status = CardBlockTicketStatus.APPROVED;
    }

    public void reject() {
        checkStatusPending();
        this.status = CardBlockTicketStatus.REJECTED;
    }

    private void checkStatusPending(){
        if(!status.equals(CardBlockTicketStatus.PENDING)){
            throw new CardBlockTicketException.CardBlockTicketStatusException("Status is not PENDING");
        }
    }

}
