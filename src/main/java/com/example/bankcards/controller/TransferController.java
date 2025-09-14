package com.example.bankcards.controller;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.facade.TransferFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Transfers", description = "Funds transfer")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class TransferController {

    private final TransferFacade facade;
    @Operation(summary = "Reject existing card block ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket rejected"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(@RequestBody @Valid TransferRequestDto request) {
        TransferResponseDto resp = facade.transferInternal(request);
        return ResponseEntity.ok().body(resp);
    }
}
