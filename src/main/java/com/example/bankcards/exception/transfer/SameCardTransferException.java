package com.example.bankcards.exception.transfer;

public class SameCardTransferException extends TransferException {
    public SameCardTransferException() {
        super("Невозможно перевести на ту же карту");
    }
}
