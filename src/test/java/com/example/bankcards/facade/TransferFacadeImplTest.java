package com.example.bankcards.facade;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.exception.TransferException;
import com.example.bankcards.facade.impl.TransferFacadeImpl;
import com.example.bankcards.mapper.TransferMapper;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferFacadeImplTest {

    @Mock
    private CardService cardService;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private TransferFacadeImpl transferFacade;

    private final Long ownerId = 1L;
    private final Long fromCardId = 10L;
    private final Long toCardId = 20L;

    @BeforeEach
    void setup() {
        when(currentUserProvider.getUserId()).thenReturn(ownerId);
        transferFacade = new TransferFacadeImpl(cardService, currentUserProvider, new TransferMapper());
    }

    @Test
    void transferInternal_ShouldWithdrawAndDeposit_AndReturnMappedResponse() {

        BigDecimal cardFromBeforeBalance = BigDecimal.TEN;
        BigDecimal cardToBeforeBalance = BigDecimal.TEN;
        BigDecimal transferAmount = BigDecimal.ONE;

        TransferRequestDto request = TransferRequestDto.builder()
                .fromCardId(fromCardId)
                .toCardId(toCardId)
                .amount(transferAmount)
                .build();

        CardEntity from = CardEntity.builder().id(fromCardId).status(CardStatus.ACTIVE).balance(cardFromBeforeBalance).build();
        CardEntity to = CardEntity.builder().id(toCardId).status(CardStatus.ACTIVE).balance(cardToBeforeBalance).build();
        when(cardService.getValidCard(ownerId, fromCardId)).thenReturn(from);
        when(cardService.getValidCard(ownerId, toCardId)).thenReturn(to);

        TransferResponseDto actual = transferFacade.transferInternal(request);

        assertEquals("SUCCESS", actual.getStatus());
        verify(cardService).withdraw(fromCardId, transferAmount);
        verify(cardService).deposit(toCardId, transferAmount);
    }

    @Test
    void transferInternal_ShouldThrowSameCardTransferException_WhenSameCardIds() {

        TransferRequestDto request = TransferRequestDto.builder()
                .fromCardId(fromCardId)
                .toCardId(fromCardId)
                .amount(BigDecimal.TEN)
                .build();
        CardEntity card = CardEntity.builder().id(fromCardId).status(CardStatus.ACTIVE).balance(BigDecimal.valueOf(100)).build();
        when(cardService.getValidCard(ownerId, fromCardId)).thenReturn(card);


        assertThrows(TransferException.SameCardTransferException.class, () -> transferFacade.transferInternal(request));

        verify(cardService, never()).withdraw(anyLong(), any());
        verify(cardService, never()).deposit(anyLong(), any());
    }

}

