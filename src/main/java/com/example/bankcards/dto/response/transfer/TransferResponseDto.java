package com.example.bankcards.dto.response.transfer;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferResponseDto {
    private BigDecimal balanceFrom;
    private String status;
}
