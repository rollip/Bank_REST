package com.example.bankcards.service;

import com.example.bankcards.dto.request.card.CardFilterDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.enums.CardStatus;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.impl.CardServiceImpl;
import com.example.bankcards.util.CardEncryptor;
import com.example.bankcards.util.CardNumberGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;


    @InjectMocks
    private CardServiceImpl cardService;

    private final Long id = 1L;


    @Nested
    @SpringBootTest(properties = "application.card.expiry-period-years=3")
    @ContextConfiguration(classes = CardServiceImpl.class)
    class CreateCardSpringTest {

        @Autowired
        private CardServiceImpl cardService;

        @MockitoBean
        private CardRepository cardRepository;

        @MockitoBean
        private CardNumberGenerator generator;

        @MockitoBean
        private CardEncryptor encryptor;

        @Value("${application.card.expiry-period-years}")
        private Integer expiryYears;

        @Test
        void create_ShouldCreateCardAndReturnIt_WhenNoDuplicateNumber() {
            Long userId = 1L;
            UserEntity userEntity = UserEntity.builder().id(userId).build();

            String cardNumber = "1234 4567 8912 3456";
            String encryptedCardNumber = "en((rYpt€dd";

            when(generator.generateCardNumber()).thenReturn(cardNumber);
            when(encryptor.encrypt(cardNumber)).thenReturn(encryptedCardNumber);
            when(cardRepository.existsByNumber(encryptedCardNumber)).thenReturn(false);
            when(cardRepository.save(any(CardEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

            CardEntity result = cardService.create(userEntity);

            assertNotNull(result);
            assertEquals(encryptedCardNumber, result.getNumber());
            assertEquals(userEntity, result.getOwner());
            assertEquals(encryptedCardNumber, result.getNumber());
            assertEquals(BigDecimal.ZERO, result.getBalance());
            assertEquals(CardStatus.ACTIVE, result.getStatus());
            assertEquals(LocalDate.now().plusYears(expiryYears), result.getExpiryDate());
        }


        @Test
        void create_ShouldThrowCardAlreadyExistsException_WhenCardNumberDuplicate() {
            Long userId = 1L;
            UserEntity userEntity = UserEntity.builder().id(userId).build();

            String cardNumber = "1234 4567 8912 3456";
            String encryptedCardNumber = "en((rYpt€dd";

            when(generator.generateCardNumber()).thenReturn(cardNumber);
            when(encryptor.encrypt(cardNumber)).thenReturn(encryptedCardNumber);
            when(cardRepository.existsByNumber(encryptedCardNumber)).thenReturn(true);

            assertThrows(CardException.CardAlreadyExistsException.class, () -> cardService.create(userEntity));
            verify(cardRepository, never()).save(any(CardEntity.class));
        }

    }


    @Test
    void block_ShouldBlockCardAndReturnBlocked() {
        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        CardStatus status = cardService.block(id);

        assertEquals(CardStatus.BLOCKED, status);
        assertEquals(CardStatus.BLOCKED, card.getStatus());

        verify(cardRepository).save(card);
    }

    @Test
    void block_ShouldThrowCardStatusExceptionWhenCardNotActive() {
        CardEntity card = CardEntity.builder().status(CardStatus.BLOCKED).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.CardStatusException.class, () -> cardService.block(id));

        verify(cardRepository, never()).save(card);
    }

    @Test
    void activate_ShouldActivateCardAndReturnActivated() {
        CardEntity card = CardEntity.builder().status(CardStatus.BLOCKED).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        CardStatus status = cardService.activate(id);

        assertEquals(CardStatus.ACTIVE, status);
        assertEquals(CardStatus.ACTIVE, card.getStatus());

        verify(cardRepository).save(card);
    }

    @Test
    void activate_ShouldThrowCardStatusExceptionWhenCardNotBlocked() {
        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.CardStatusException.class, () -> cardService.activate(id));

        verify(cardRepository, never()).save(card);
    }


    @Test
    void deposit_ShouldAddFunds_WhenCardIsValid() {
        BigDecimal initBalance = BigDecimal.ONE;
        BigDecimal amount = BigDecimal.TEN;
        BigDecimal expectedBalance = initBalance.add(amount);

        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).balance(initBalance).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        BigDecimal result = cardService.deposit(id, amount);

        assertEquals(expectedBalance, card.getBalance());
        assertEquals(CardStatus.ACTIVE, card.getStatus());
        assertEquals(expectedBalance, result);

        verify(cardRepository).save(card);
    }

    @Test
    void deposit_ShouldThrowCardStatusException_WhenCardIsNotActive() {
        BigDecimal initBalance = BigDecimal.ONE;
        BigDecimal amount = BigDecimal.TEN;

        CardEntity card = CardEntity.builder().status(CardStatus.BLOCKED).balance(initBalance).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.CardStatusException.class, () -> cardService.deposit(id, amount));
        assertEquals(CardStatus.BLOCKED, card.getStatus());
        verify(cardRepository, never()).save(card);
    }


    @Test
    void withdraw_ShouldSubtract_WhenCardIsValidAndFundsSufficient() {
        BigDecimal initBalance = BigDecimal.TEN;
        BigDecimal amount = BigDecimal.ONE;
        BigDecimal expectedBalance = initBalance.subtract(amount);

        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).balance(initBalance).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        BigDecimal result = cardService.withdraw(id, amount);

        assertEquals(expectedBalance, card.getBalance());
        assertEquals(expectedBalance, result);
        assertEquals(CardStatus.ACTIVE, card.getStatus());

        verify(cardRepository).save(card);
    }

    @Test
    void withdraw_ShouldThrowCardStatusException_WhenCardIsNotActive() {
        BigDecimal initBalance = BigDecimal.ONE;
        BigDecimal amount = BigDecimal.TEN;

        CardEntity card = CardEntity.builder().status(CardStatus.BLOCKED).balance(initBalance).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.CardStatusException.class, () -> cardService.withdraw(id, amount));
        assertEquals(CardStatus.BLOCKED, card.getStatus());
        verify(cardRepository, never()).save(card);
    }

    @Test
    void withdraw_ShouldThrowFundsException_WhenCardIsActiveAndFundsInsufficient() {
        BigDecimal initBalance = BigDecimal.ONE;
        BigDecimal amount = BigDecimal.TEN;

        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).balance(initBalance).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.CardFundsException.class, () -> cardService.withdraw(id, amount));
        assertEquals(CardStatus.ACTIVE, card.getStatus());
        verify(cardRepository, never()).save(card);
    }

    @Test
    void delete_ShouldDeleteCardAndReturnCard() {

        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        cardService.delete(id);

        verify(cardRepository).delete(card);
    }

    @Test
    void getAllCards_ShouldReturnAllCards() {

        Long id1 = 1L;
        Long id2 = 2L;

        CardEntity card1 = CardEntity.builder().id(id1).status(CardStatus.ACTIVE).build();
        CardEntity card2 = CardEntity.builder().id(id2).status(CardStatus.ACTIVE).build();
        List<CardEntity> cards = List.of(card1, card2);

        when(cardRepository.findAll()).thenReturn(cards);

        List<CardEntity> result = cardService.getAllCards();

        assertEquals(cards.size(), result.size());
        assertEquals(id1, result.get(0).getId());
        assertEquals(id2, result.get(1).getId());

    }


    @Test
    void getCardsForUser_ShouldReturnPage_WhenFilterIsPresent() {

        CardFilterDto filter = new CardFilterDto();
        filter.setMinBalance(BigDecimal.ZERO);
        filter.setMaxBalance(BigDecimal.valueOf(1000));
        filter.setNumber("1234");
        filter.setExpiryFrom(LocalDate.now());
        filter.setExpiryTo(LocalDate.now().plusYears(5));

        Pageable pageable = PageRequest.of(0, 10);
        Long userId = 1L;

        CardEntity card = CardEntity.builder().id(1L).build();
        Page<CardEntity> page = new PageImpl<>(List.of(card), pageable, 1);

        when(cardRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(page);

        Page<CardEntity> result = cardService.getCardsForUser(filter, pageable, userId);

        assertEquals(1, result.getTotalElements());
        assertEquals(card, result.getContent().get(0));
        verify(cardRepository).findAll(any(Specification.class), eq(pageable));

    }

    @Test
    void getCardsForUser_ShouldReturnPage_WhenFilterIsAbsent() {
        Pageable pageable = PageRequest.of(0, 10);
        Long userId = 1L;

        CardEntity card = CardEntity.builder().id(1L).build();
        Page<CardEntity> page = new PageImpl<>(List.of(card), pageable, 1);

        when(cardRepository.findByOwner_Id(userId, pageable))
                .thenReturn(page);

        Page<CardEntity> result = cardService.getCardsForUser(null, pageable, userId);

        assertEquals(1, result.getTotalElements());
        assertEquals(card, result.getContent().get(0));
        verify(cardRepository).findByOwner_Id(userId, pageable);

    }


    @Test
    void getValidCard_ShouldReturnCardIfValid() {
        Long userId = 1L;
        UserEntity user = UserEntity.builder().id(userId).build();
        CardEntity card = CardEntity.builder().owner(user).status(CardStatus.ACTIVE).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        CardEntity result = cardService.getValidCard(id, userId);

        assertEquals(card, result);
    }

    @Test
    void getValidCard_ShouldThrowIfWrongUser() {
        Long userId = 1L;
        Long otherUserId = 2L;
        UserEntity user = UserEntity.builder().id(userId).build();
        CardEntity card = CardEntity.builder().owner(user).status(CardStatus.ACTIVE).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.UnauthorizedCardAccessException.class,
                () -> cardService.getValidCard(id, otherUserId));
    }

    @Test
    void getValidCard_ShouldThrowIfBlocked() {
        Long userId = 1L;
        UserEntity user = UserEntity.builder().id(userId).build();
        CardEntity card = CardEntity.builder().owner(user).status(CardStatus.BLOCKED).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));

        assertThrows(CardException.CardStatusException.class,
                () -> cardService.getValidCard(id, userId));
    }

    @Test
    void getCard_ShouldReturnCard() {
        CardEntity card = CardEntity.builder().status(CardStatus.ACTIVE).build();
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        assertEquals(card, cardService.getCard(id));
    }

    @Test
    void getCard_ShouldThrowCardExceptionWhenNoCardFound() {
        when(cardRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cardService.getCard(id));
    }


}
