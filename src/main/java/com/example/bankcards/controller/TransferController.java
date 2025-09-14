package com.example.bankcards.controller;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.facade.TransferFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class TransferController {

    private final TransferFacade facade;

    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(@RequestBody @Valid TransferRequestDto request) {
        TransferResponseDto resp = facade.transferInternal(request);
        return ResponseEntity.ok().body(resp);
    }
}
