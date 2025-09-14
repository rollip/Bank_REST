package com.example.bankcards.controller;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.facade.CardFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardFacade facade;

    @GetMapping
    public ResponseEntity<Page<CardDto>> getCards(Pageable pageable,
                                                  @RequestBody(required = false) Optional<CardFilterDto> filterDto){
        CardFilterDto filter = filterDto.orElse(new CardFilterDto());
        Page<CardDto> dtos = facade.getCardsForUser(filter, pageable);
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CardDto>> getAllCards(){
        List<CardDto> dtos = facade.getAllCards();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateCardResponseDto> create(@RequestBody @Valid CreateCardRequestDto request){
        CreateCardResponseDto dto =  facade.create(request);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardStatus> block(@PathVariable Long id){
        CardStatus status =  facade.block(id);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardStatus> activate(@PathVariable Long id){
        CardStatus status =  facade.activate(id);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        facade.delete(id);
        return ResponseEntity.noContent().build();
    }

}
