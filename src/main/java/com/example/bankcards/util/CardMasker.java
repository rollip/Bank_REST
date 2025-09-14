package com.example.bankcards.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CardMasker {

    @Value("${application.card.number.length}")
    public Integer cardLength;

    public String mask(String number) {
        if (number.length() != cardLength) {
            throw new IllegalArgumentException("Invalid card length");
        }
        return "**** **** **** ".concat(number.substring(12));
    }
}
