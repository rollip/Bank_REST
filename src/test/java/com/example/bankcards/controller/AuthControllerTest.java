package com.example.bankcards.controller;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.facade.AuthFacade;
import com.example.bankcards.security.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    private final String ROOT_URI = "/auth";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AuthFacade authFacade;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void register_ShouldReturnToken_WhenRequestValid() throws Exception {
        RegisterRequest request = new RegisterRequest("testUser", "password123");
        RegisterResponse response = new RegisterResponse("mocked-jwt-token");

        when(authFacade.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(response);

        mvc.perform(post(ROOT_URI+"/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()))
                .andExpect(jsonPath("$.token", is(response.getToken())));
    }

    @Test
    void login_ShouldReturnToken_WhenRequestValid() throws Exception {

        LoginRequest request = new LoginRequest("testUser", "password123");
        LoginResponse response = new LoginResponse("mocked-jwt-token");

        when(authFacade.login(any(LoginRequest.class)))
                .thenReturn(response);

        mvc.perform(post(ROOT_URI+"/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()))
                .andExpect(jsonPath("$.token", is(response.getToken())));
    }
}
