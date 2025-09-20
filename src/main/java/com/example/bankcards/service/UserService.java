package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.exception.UserException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service implementation for managing application users.
 *
 * <p>This service provides operations for creating, finding, and deleting users.
 * Business rules:</p>
 * <ul>
 *     <li>Usernames must be unique.</li>
 *     <li>Passwords are stored in encrypted form using {@link PasswordEncoder}.</li>
 * </ul>
 */
public interface UserService {

    /**
     * Finds a user by ID.
     *
     * @param id the user ID
     * @return the {@link UserEntity} with the given ID
     * @throws NotFoundException if no user with the given ID exists
     */
    UserEntity findById(Long id);

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return the {@link UserEntity} with the given username
     * @throws NotFoundException if no user with the given username exists
     */
    UserEntity findByUsername(String username);

    /**
     * Creates a new user with the specified username and password.
     *
     * <p>The password will be encoded before storage. Usernames must be unique.</p>
     *
     * @param username the username for the new user
     * @param password the raw password
     * @return the created {@link UserEntity}
     * @throws UserException.UserAlreadyExistsException if a user with the given username already exists
     */
    UserEntity create(String username, String password);

    /**
     * Deletes a user by ID.
     *
     * @param id the user ID
     * @throws NotFoundException if the user does not exist
     */
    void delete(Long id);

}
