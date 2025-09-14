package com.example.bankcards.exception;

public abstract class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }

    public static class SameCardTransferException extends TransferException {
        public SameCardTransferException() {
            super("Can't transfer a same card");
        }
    }
}
