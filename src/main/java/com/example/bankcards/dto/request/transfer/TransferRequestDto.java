package com.example.bankcards.dto.request.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    @NotNull(message = "fromCardId can't be null")
    private Long fromCardId;
    @NotNull(message = "toCardId can't be null")
    private Long toCardId;
    @NotNull(message = "amount can't be null")
    @DecimalMin(value = "0.01", message = "Amount has to be greater than 0")
    private BigDecimal amount;
}
