package com.example.bankcards.facade;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;

public interface TransferFacade {

    TransferResponseDto transferInternal(TransferRequestDto transferRequestDto);
}