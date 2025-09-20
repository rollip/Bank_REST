package com.example.bankcards.facade;

import com.example.bankcards.exception.NotFoundException;

/**
 * Facade for managing users.
 * Provides operations for administrative actions on users.
 */
public interface UserFacade {

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @throws NotFoundException if the user does not exist
     */
    void delete(Long id);
}