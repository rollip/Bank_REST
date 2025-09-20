package com.example.bankcards.facade;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.exception.TransferException;
import com.example.bankcards.exception.NotFoundException;


/**
 * Facade for managing funds transfers.
 * Provides operations for executing transfers between cards
 * within the system.
 * Business rules:
 * <ul>
 *     <li>Transfers can only be performed between active cards.</li>
 *     <li>The source card must have sufficient balance.</li>
 *     <li>Transfers cannot be made to the same card.</li>
 * </ul>
 */
public interface TransferFacade {

    /**
     * Executes an internal transfer between two accounts.
     *
     * @param transferRequestDto DTO containing transfer details
     * @return DTO representing the result of the transfer
     * @throws NotFoundException if one of the accounts does not exist
     * @throws TransferException if the transfer violates business rules
     */
    TransferResponseDto transferInternal(TransferRequestDto transferRequestDto);
}