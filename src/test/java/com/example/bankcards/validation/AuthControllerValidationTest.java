package com.example.bankcards.validation;

import com.example.bankcards.controller.AuthController;
import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.facade.AuthFacade;
import com.example.bankcards.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerValidationTest {


    private final String ROOT_URI = "/auth";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AuthFacade authFacade;

    @MockitoBean
    private JwtService jwtService;


    @Test
    void register_ShouldReturn400_WhenLoginIsBlank() throws Exception {
        RegisterRequest request = new RegisterRequest(" ", "password123");

        mvc.perform(post(ROOT_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_ShouldReturn400_WhenPasswordIsBlank() throws Exception {
        RegisterRequest request = new RegisterRequest("testUser", " ");

        mvc.perform(post(ROOT_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("login_ShouldReturn400_WhenLoginIsBlank")
    void login_ShouldReturn400_WhenLoginIsBlank() throws Exception {
        LoginRequest request = new LoginRequest(" ", "password123");

        mvc.perform(post(ROOT_URI+"/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("login_ShouldReturn400_WhenPasswordIsBlank")
    void login_ShouldReturn400_WhenPasswordIsBlank() throws Exception {
        LoginRequest request = new LoginRequest("testUser", " ");

        mvc.perform(post(ROOT_URI+"/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

