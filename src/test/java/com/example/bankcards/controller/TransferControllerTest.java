package com.example.bankcards.controller;

import com.example.bankcards.dto.request.transfer.TransferRequestDto;
import com.example.bankcards.dto.response.transfer.TransferResponseDto;
import com.example.bankcards.facade.TransferFacade;
import com.example.bankcards.security.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransferControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @MockitoBean
    private TransferFacade transferFacade;

    private final String ROOT_URI = "/transfers";

    @Test
    void transferShouldReturnSuccess() throws Exception {
        TransferRequestDto request = TransferRequestDto.builder()
                .fromCardId(1L)
                .toCardId(2L)
                .amount(BigDecimal.TEN)
                .build();

        TransferResponseDto response = TransferResponseDto.builder()
                .balanceFrom(BigDecimal.ZERO)
                .status("SUCCESS")
                .build();

        when(transferFacade.transferInternal(request)).thenReturn(response);

        mvc.perform(post(ROOT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(response.getStatus()));
    }
}

