package com.example.bankcards.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class CardEncryptor {


    private final TextEncryptor encryptor;

    public CardEncryptor(
            @Value("${application.card.encryption.key}") String key,
            @Value("${application.card.encryption.salt}") String salt) {
        this.encryptor = Encryptors.text(key, salt);
    }

    public String encrypt(String cardNumber) {
        return encryptor.encrypt(cardNumber);
    }

    public String decrypt(String encrypted) {
        return encryptor.decrypt(encrypted);
    }

}
