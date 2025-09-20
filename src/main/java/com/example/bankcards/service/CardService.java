package com.example.bankcards.service;


import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service implementation for managing bank cards.
 *
 * <p>This service handles card creation, activation, blocking, deposit and withdrawal,
 * as well as fetching cards for users.</p>
 *
 * <p>
 * Business rules:
 * <ul>
 *     <li>A card must belong to the user performing operations.</li>
 *     <li>Blocked cards cannot be used for deposit or withdrawal.</li>
 *     <li>Card numbers must be unique.</li>
 *     <li>Card numbers must be stored encrypted in db.</li>
 * </ul>
 * </p>
 */
public interface CardService {

    /**
     * Creates a new card for the given user.
     *
     * @param user the card owner
     * @return the created {@link CardEntity}
     * @throws CardException.CardAlreadyExistsException if a generated card number already exists
     */
    CardEntity create(UserEntity user);

    /**
     * Blocks a card by ID.
     *
     * @param id the card ID
     * @return the updated {@link CardStatus} after blocking
     * @throws NotFoundException if card with given ID does not exist
     */
    CardStatus block(Long id);

    /**
     * Activates a card by ID.
     *
     * @param id the card ID
     * @return the updated {@link CardStatus} after activation
     * @throws NotFoundException if card with given ID does not exist
     */
    CardStatus activate(Long id);

    /**
     * Deposits funds into the card.
     *
     * @param id     the card ID
     * @param amount the amount to deposit
     * @return the new balance after deposit
     * @throws NotFoundException                 if card with given ID does not exist
     * @throws CardException.CardStatusException if card is blocked
     */
    BigDecimal deposit(Long id, BigDecimal amount);

    /**
     * Withdraws funds from the card.
     *
     * @param id     the card ID
     * @param amount the amount to withdraw
     * @return the new balance after withdrawal
     * @throws NotFoundException                 if card with given ID does not exist
     * @throws CardException.CardStatusException if card is blocked
     * @throws CardException.CardFundsException  if insufficient funds
     */
    BigDecimal withdraw(Long id, BigDecimal amount);

    /**
     * Deletes a card by ID.
     *
     * @param id the card ID
     * @throws NotFoundException if card with given ID does not exist
     */
    void delete(Long id);

    /**
     * Retrieves cards for a user, optionally filtered by {@link CardFilterDto}.
     *
     * @param filter   filter criteria, can be null
     * @param pageable paging parameters
     * @param userId   the owner user ID
     * @return a page of cards matching the filter
     */
    Page<CardEntity> getCardsForUser(CardFilterDto filter, Pageable pageable, Long userId);

    /**
     * Returns all cards in the database.
     *
     * @return list of all {@link CardEntity}
     */
    List<CardEntity> getAllCards();

    /**
     * Returns a card by ID.
     *
     * @param id the card ID
     * @return the {@link CardEntity}
     * @throws NotFoundException if card not found
     */
    CardEntity getCard(Long id);

    /**
     * Returns a card for a specific user, ensuring it is active and belongs to the user.
     *
     * @param ownerId the user ID
     * @param cardId  the card ID
     * @return the valid {@link CardEntity}
     * @throws NotFoundException                             if card not found
     * @throws CardException.UnauthorizedCardAccessException if card does not belong to user
     * @throws CardException.CardStatusException             if card is blocked
     */
    CardEntity getValidCard(Long ownerId, Long cardId);
}
