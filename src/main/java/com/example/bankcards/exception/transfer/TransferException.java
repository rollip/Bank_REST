package com.example.bankcards.exception.transfer;

public abstract class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }
}
