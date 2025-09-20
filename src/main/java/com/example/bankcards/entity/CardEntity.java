package com.example.bankcards.entity;

import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.exception.CardException;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status = CardStatus.ACTIVE;

    @Builder.Default
    @DecimalMin(value = "0.00", message = "Balance must be positive")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity owner;

    public void block(){
        ensureActive();
        status = CardStatus.BLOCKED;
    }

    public void activate() {
        ensureBlocked();
        this.status = CardStatus.ACTIVE;
    }

    public void deposit(BigDecimal amount) {
        ensureActive();
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        ensureActive();
        ensureSufficientBalance(amount);
        balance = balance.subtract(amount);
    }

    private void ensureActive(){
        if (this.status != CardStatus.ACTIVE) {
            throw new CardException.CardStatusException("Card is not active");
        }
    }

    private void ensureBlocked(){
        if (this.status != CardStatus.BLOCKED) {
            throw new CardException.CardStatusException("Card is not blocked");
        }
    }


    private void ensureSufficientBalance(BigDecimal amount){
        if (this.balance.compareTo(amount) < 0) {
            throw new CardException.CardFundsException("Insufficient funds");
        }
    }




}
