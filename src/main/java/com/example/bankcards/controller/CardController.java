package com.example.bankcards.controller;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.facade.CardFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cards", description = "Cards management")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardFacade facade;

    @Operation(summary = "Get cards for current logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards listed paged"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/search")
    public ResponseEntity<Page<CardDto>> getCardsForCurrentUser(Pageable pageable,
                                                                @RequestBody(required = false) CardFilterDto filterDto) {
        Page<CardDto> dtos = facade.getCardsForCurrentUser(filterDto, pageable);
        return ResponseEntity.ok().body(dtos);
    }

    @Operation(summary = "Get all cards from database for admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards listed"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CardDto>> getAllCards() {
        List<CardDto> dtos = facade.getAllCards();
        return ResponseEntity.ok().body(dtos);
    }

    @Operation(summary = "Create card for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card created and returned in body"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "404", description = "Card owner is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateCardResponseDto> create(@RequestBody @Valid CreateCardRequestDto request) {
        CreateCardResponseDto dto = facade.create(request);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Block card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card blocked"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "404", description = "Card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardStatus> block(@PathVariable Long id) {
        CardStatus status = facade.block(id);
        return ResponseEntity.ok().body(status);
    }

    @Operation(summary = "Activate card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card activated"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "404", description = "Card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardStatus> activate(@PathVariable Long id) {
        CardStatus status = facade.activate(id);
        return ResponseEntity.ok(status);
    }

    @Operation(summary = "Delete card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden — user lacks required role"),
            @ApiResponse(responseCode = "404", description = "Card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity.noContent().build();
    }

}
