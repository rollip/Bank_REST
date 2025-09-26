package com.example.bankcards.facade;

import com.example.bankcards.dto.request.cardBlockTicket.CreateCardBlockTicketRequestDto;
import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.exception.CardBlockTicketException;
import com.example.bankcards.exception.NotFoundException;

/**
 * Facade for managing card block tickets.
 * This interface provides operations for creating, approving, and rejecting
 * card block tickets, encapsulating the underlying service layer logic.
 * Business rules:
 * <ul>
 *     <li>Only one pending ticket can exist for a card at a time.</li>
 *     <li>Tickets must be in {@code PENDING} status to be approved or rejected.</li>
 * </ul>
 */
public interface CardBlockTicketFacade {
    /**
     * Creates a new card block ticket for the specified card.
     *
     * @param requestDto the DTO containing card information
     * @return a {@link CreateCardBlockTicketResponseDto} DTO representing the created ticket
     * @throws CardBlockTicketException if there is already a pending ticket for this card
     */
    CreateCardBlockTicketResponseDto create(CreateCardBlockTicketRequestDto requestDto);

    /**
     * Approves a pending card block ticket, changing its status to {@code APPROVED}.
     *
     * @param id the ticket ID
     * @return the updated status of the ticket {@link CardBlockTicketStatus}
     * @throws NotFoundException                                       if the ticket does not exist
     * @throws CardBlockTicketException.CardBlockTicketStatusException if the ticket is not in {@code PENDING} status
     */
    CardBlockTicketStatus approve(Long id);

    /**
     * Rejects a pending card block ticket, changing its status to {@code REJECTED}.
     *
     * @param id the ticket ID
     * @return the updated status of the ticket {@link CardBlockTicketStatus}
     * @throws NotFoundException                                       if the ticket does not exist
     * @throws CardBlockTicketException.CardBlockTicketStatusException if the ticket is not in {@code PENDING} status
     */
    CardBlockTicketStatus reject(Long id);
}
