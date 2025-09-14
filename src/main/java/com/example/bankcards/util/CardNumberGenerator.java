package com.example.bankcards.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CardNumberGenerator {

    @Value("${application.card.number.length}")
    public Integer cardLength;

    public String generateCardNumber() {
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder(cardLength);
        for (int i = 0; i < cardLength; i++) {
            int number = rand.nextInt(10);
            sb.append(number);
        }
        return sb.toString();
    }

}
