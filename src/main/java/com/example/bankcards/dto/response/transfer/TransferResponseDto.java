package com.example.bankcards.dto.response.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Response returned after a successful transfer")
public class TransferResponseDto {

    @Schema(description = "Remaining balance on the source card after transfer", example = "850.75")
    private BigDecimal balanceFrom;

    @Schema(description = "Status of the transfer operation", example = "SUCCESS")
    private String status;
}
