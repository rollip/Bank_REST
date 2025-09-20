package com.example.bankcards.validation;

import com.example.bankcards.controller.TransferController;
import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.facade.TransferFacade;
import com.example.bankcards.security.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransferControllerValidationTest {

    private final String ROOT_URI = "/transfers";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @MockitoBean
    private TransferFacade transferFacade;


    @Test
    void transfer_ShouldReturn400_WhenFromCardIdIsNull() throws Exception {
        TransferRequestDto request = TransferRequestDto.builder()
                .toCardId(1L)
                .amount(BigDecimal.ONE)
                .build();

        mvc.perform(post(ROOT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void transfer_ShouldReturn400_WhenToCardIdIsNull() throws Exception {
        TransferRequestDto request = TransferRequestDto.builder()
                .fromCardId(1L)
                .amount(BigDecimal.ONE)
                .build();

        mvc.perform(post(ROOT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullSource
    @CsvSource({"0.0", "-1.0"})
    void transfer_ShouldReturn400_WhenAmountInvalid(BigDecimal amount) throws Exception {
        TransferRequestDto request = TransferRequestDto.builder()
                .fromCardId(1L)
                .toCardId(2L)
                .amount(amount)
                .build();

        mvc.perform(post(ROOT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}

