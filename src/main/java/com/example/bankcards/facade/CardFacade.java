package com.example.bankcards.facade;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.NotFoundException;

import java.util.List;

/**
 * Facade for managing bank cards.
 * This interface provides operations for creating, activating, blocking,
 * getting, and deleting cards.
 * Business rules:
 * <ul>
 *     <li>Card must belong to the current user to be retrieved or modified.</li>
 *     <li>Only inactive cards can be activated.</li>
 *     <li>Only active cards can be blocked.</li>
 *     <li>Deleted cards cannot be restored.</li>
 * </ul>
 */
public interface CardFacade {

    /**
     * Retrieves all cards in the system.
     *
     * @return list of all cards {@link CardDto}
     */
    List<CardDto> getAllCards();

    /**
     * Retrieves cards for the currently authenticated user,
     * with optional filtering and pagination.
     *
     * @param filterDto filtering criteria
     * @param pageable  pagination parameters
     * @return page of cards {@link CardDto}  belonging to the current user
     */
    Page<CardDto> getCardsForCurrentUser(CardFilterDto filterDto, Pageable pageable);

    /**
     * Deletes a card by its ID.
     *
     * @param id the ID of the card to delete
     * @throws NotFoundException if the card does not exist
     */
    void delete(Long id);

    /**
     * Activates a card by its ID.
     *
     * @param cardId the ID of the card to activate
     * @return {@link CardStatus}  updated status of the card
     * @throws com.example.bankcards.exception.NotFoundException if the card does not exist
     * @throws CardException.CardStatusException                 if the card is not in a state that can be activated
     */
    CardStatus activate(Long cardId);

    /**
     * Blocks a card by its ID.
     *
     * @param cardId the ID of the card to block
     * @return updated status of the card
     * @throws NotFoundException                 if the card does not exist
     * @throws CardException.CardStatusException if the card is not in a state that can be blocked
     */
    CardStatus block(Long cardId);

    /**
     * Creates a new card.
     *
     * @param request DTO containing card creation details
     * @return {@link CreateCardResponseDto} DTO representing the newly created card
     * @throws CardException.CardAlreadyExistsException if the card cannot be created due to business rule violations
     */
    CreateCardResponseDto create(CreateCardRequestDto request);
}
