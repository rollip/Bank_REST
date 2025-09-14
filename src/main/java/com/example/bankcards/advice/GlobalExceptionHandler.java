package com.example.bankcards.advice;

import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.CardBlockTicketException;
import com.example.bankcards.exception.TransferException;
import com.example.bankcards.exception.UserException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<Map<String, String>> handle(AuthenticationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<Map<String, String>> handle(LoginException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(CardException.class)
    public ResponseEntity<Map<String, String>> handleCardStatus(CardException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<Map<String, String>> handleTransfer(TransferException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, String>> handleUser(UserException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(CardBlockTicketException.class)
    public ResponseEntity<Map<String, String>> handleCardBlockTicket(CardBlockTicketException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, String>> handleOther() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred. Please contact support."));
    }

}
