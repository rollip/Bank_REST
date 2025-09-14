package com.example.bankcards.controller;

import com.example.bankcards.dto.request.cardBlockTicket.CreateCardBlockTicketRequestDto;
import com.example.bankcards.dto.response.cardBlockTicket.CreateCardBlockTicketResponseDto;
import com.example.bankcards.enums.CardBlockTicketStatus;
import com.example.bankcards.facade.CardBlockTicketFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card-block-requests")
public class CardBlockTicketController {

    private final CardBlockTicketFacade facade;

    @PostMapping
    public ResponseEntity<CreateCardBlockTicketResponseDto> create (@Valid @RequestBody CreateCardBlockTicketRequestDto request) {
        CreateCardBlockTicketResponseDto dto = facade.create(request);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/approve")
    public ResponseEntity<CardBlockTicketStatus> approve (@PathVariable Long id) {
        CardBlockTicketStatus status = facade.approve(id);
        return ResponseEntity.ok().body(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/reject")
    public ResponseEntity<CardBlockTicketStatus> reject (@PathVariable Long id) {
        CardBlockTicketStatus status = facade.reject(id);
        return ResponseEntity.ok().body(status);
    }


}
