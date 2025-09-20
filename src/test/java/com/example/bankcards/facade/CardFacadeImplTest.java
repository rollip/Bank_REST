package com.example.bankcards.facade;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.dto.request.card.CreateCardRequestDto;
import com.example.bankcards.dto.response.card.CardDto;
import com.example.bankcards.dto.response.card.CreateCardResponseDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.facade.impl.CardFacadeImpl;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardFacadeImplTest {
    @Mock
    private CardService cardService;
    @Mock
    private CardMapper mapper;
    @Mock
    private UserService userService;
    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private CardFacadeImpl cardFacade;


    @Test
    void getCardsForCurrentUser_ShouldReturnMappedPage() {
        CardFilterDto filter = new CardFilterDto();
        Pageable pageable = Pageable.unpaged();
        Long userId = 1L;

        CardEntity entity = CardEntity.builder().id(1L).build();
        CardDto dto = CardDto.builder().id(21L).build();

        when(currentUserProvider.getUserId()).thenReturn(userId);
        when(cardService.getCardsForUser(filter, pageable, userId))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(dto);

        Page<CardDto> result = cardFacade.getCardsForCurrentUser(filter, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().get(0));

        verify(currentUserProvider).getUserId();
        verify(cardService).getCardsForUser(filter, pageable, userId);
        verify(mapper).toDto(entity);
    }

    @Test
    void create_ShouldCreateCard() {
        Long userId = 1L;
        CreateCardRequestDto request = new CreateCardRequestDto(userId);
        UserEntity userEntity = UserEntity.builder().id(userId).build();
        Long cardId = 2L;
        CardEntity card = CardEntity.builder().id(cardId).owner(userEntity).build();
        CreateCardResponseDto expectedResponse = CreateCardResponseDto.builder()
                .id(cardId)
                .build();
        when(userService.findById(request.getOwnerId())).thenReturn(userEntity);
        when(cardService.create(userEntity)).thenReturn(card);
        when(mapper.toCreateResponse(card)).thenReturn(expectedResponse);

        CreateCardResponseDto response = cardFacade.create(request);

        assertEquals(expectedResponse.getId(), response.getId());
        verify(cardService).create(userEntity);
    }


    @Test
    void getAllCards_ShouldReturnMappedDtos() {
        CardEntity card1 = CardEntity.builder().id(1L).build();
        CardEntity card2 = CardEntity.builder().id(2L).build();

        CardDto dto1 = CardDto.builder().id(1L).build();
        CardDto dto2 = CardDto.builder().id(2L).build();

        when(cardService.getAllCards()).thenReturn(List.of(card1, card2));
        when(mapper.toDto(card1)).thenReturn(dto1);
        when(mapper.toDto(card2)).thenReturn(dto2);

        List<CardDto> result = cardFacade.getAllCards();

        assertEquals(2, result.size());
        assertEquals(List.of(dto1, dto2), result);
        verify(cardService).getAllCards();
        verify(mapper).toDto(card1);
        verify(mapper).toDto(card2);
    }

    @Test
    void activate_ShouldCallServiceAndReturnStatus() {
        Long cardId = 1L;
        when(cardService.activate(cardId)).thenReturn(CardStatus.ACTIVE);

        CardStatus status = cardFacade.activate(cardId);

        assertEquals(CardStatus.ACTIVE, status);
        verify(cardService).activate(cardId);
    }

    @Test
    void block_ShouldCallServiceAndReturnStatus() {
        Long cardId = 2L;
        when(cardService.block(cardId)).thenReturn(CardStatus.BLOCKED);

        CardStatus status = cardFacade.block(cardId);

        assertEquals(CardStatus.BLOCKED, status);
        verify(cardService).block(cardId);
    }

    @Test
    void delete_ShouldCallService() {
        Long cardId = 3L;

        cardFacade.delete(cardId);

        verify(cardService).delete(cardId);
    }

}
