package com.example.bankcards.dto.request.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {

    @NotNull(message = "fromCardId can't be null")
    @Schema(description = "ID of the source card", example = "101")
    private Long fromCardId;

    @NotNull(message = "toCardId can't be null")
    @Schema(description = "ID of the destination card", example = "202")
    private Long toCardId;

    @NotNull(message = "amount can't be null")
    @DecimalMin(value = "0.01", message = "Amount has to be greater than 0")
    @Schema(description = "Amount to transfer (minimum 0.01)", example = "150.50")
    private BigDecimal amount;
}
