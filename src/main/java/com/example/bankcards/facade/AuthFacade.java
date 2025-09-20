package com.example.bankcards.facade;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.exception.UserException.UserAlreadyExistsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Facade for authentication operations.
 * Provides methods for registering new users and logging in existing users.
 * This interface abstracts the underlying authentication logic and JWT handling.
 */
public interface AuthFacade {
    /**
     * Registers a new user account.
     * The registration process includes validation of the input data and
     * creation of a new user. On successful registration,
     * a JWT token is generated and returned as part of the response both header and body.
     *
     * @param request the request {@link RegisterRequest} containing username and password
     * @return a {@link RegisterResponse} containing the generated JWT token
     * @throws UserAlreadyExistsException if a user with the given username already exists
     */
    RegisterResponse register(RegisterRequest request);

    /**
     * Authenticates an existing user.
     * Validates the provided credentials and, if successful, returns a JWT token.
     *
     * @param request the login request {@link LoginRequest} containing username and password
     * @return a {@link LoginResponse} containing the JWT token
     * @throws InternalAuthenticationServiceException if the credentials are invalid
     */
    LoginResponse login(LoginRequest request);
}
