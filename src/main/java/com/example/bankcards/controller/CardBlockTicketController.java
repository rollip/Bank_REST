package com.example.bankcards.controller;

import com.example.bankcards.dto.request.cardBlockTicket.CreateCardBlockTicketRequestDto;
import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.facade.CardBlockTicketFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Block card tickets", description = "Tickets for card blocking management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/card-block-requests")
public class CardBlockTicketController {

    private final CardBlockTicketFacade facade;

    @Operation(summary = "Create a new card block ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CreateCardBlockTicketResponseDto> create(@Valid @RequestBody CreateCardBlockTicketRequestDto request) {
        CreateCardBlockTicketResponseDto dto = facade.create(request);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Approve existing card block ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket approved"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/approve")
    public ResponseEntity<CardBlockTicketStatus> approve(@PathVariable Long id) {
        CardBlockTicketStatus status = facade.approve(id);
        return ResponseEntity.ok().body(status);
    }


    @Operation(summary = "Reject existing card block ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket rejected"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/reject")
    public ResponseEntity<CardBlockTicketStatus> reject(@PathVariable Long id) {
        CardBlockTicketStatus status = facade.reject(id);
        return ResponseEntity.ok().body(status);
    }


}
