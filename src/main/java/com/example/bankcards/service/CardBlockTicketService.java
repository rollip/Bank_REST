package com.example.bankcards.service;

import com.example.bankcards.entity.CardBlockTicketEntity;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.exception.CardBlockTicketException;
import com.example.bankcards.exception.NotFoundException;

/**
 * Service implementation for managing card block tickets.
 * <p>
 * A card block ticket represents a request to block a card. This service allows
 * creating new tickets, retrieving them, and approving or rejecting requests.
 * </p>
 *
 * <p>
 * Business rules:
 * <ul>
 *   <li>Only one pending ticket can exist for a card at a time.</li>
 *   <li>Ticket status must be {@code PENDING} to approve or reject it.</li>
 * </ul>
 * </p>
 */
public interface CardBlockTicketService {
    /**
     * Creates a new card block ticket for the specified card.
     *
     * @param cardEntity the card to block
     * @return the created {@link CardBlockTicketEntity}
     * @throws CardBlockTicketException.CardBlockTicketStatusException if a pending ticket already exists
     */
    CardBlockTicketEntity create(CardEntity cardEntity);

    /**
     * Approves a pending ticket, updating its status to {@link CardBlockTicketStatus#APPROVED}.
     *
     * @param id the ticket ID
     * @return the updated ticket status {@link CardBlockTicketStatus}
     * @throws NotFoundException if no ticket with such ID exists
     */
    CardBlockTicketStatus approve(Long id);

    /**
     * Rejects a pending ticket, updating its status to {@link CardBlockTicketStatus#REJECTED}.
     *
     * @param id the ticket ID
     * @return the updated ticket status {@link CardBlockTicketStatus}
     * @throws NotFoundException if no ticket with such ID exists
     */
    CardBlockTicketStatus reject(Long id);

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id the ticket ID
     * @return the ticket entity {@link CardBlockTicketEntity}
     * @throws NotFoundException if no ticket with such ID exists
     */
    CardBlockTicketEntity get(Long id);

    /**
     * Retrieves a ticket by ID, ensuring it is still pending.
     *
     * @param id the ticket ID
     * @return the ticket entity {@link CardBlockTicketEntity} (pending)
     * @throws CardBlockTicketException.CardBlockTicketStatusException if the ticket is not in {@code PENDING} status
     */
    CardBlockTicketEntity getIfPending(Long id);

}
