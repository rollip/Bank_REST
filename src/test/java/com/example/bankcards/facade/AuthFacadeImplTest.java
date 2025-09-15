package com.example.bankcards.facade;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.facade.impl.AuthFacadeImpl;
import com.example.bankcards.mapper.AuthMapper;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthFacadeImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private Authentication auth;

    @InjectMocks
    private AuthFacadeImpl authFacade;



    private UserEntity userEntity;
    private String generatedToken;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder().username("testUser").build();

        generatedToken = "mocked-jwt-token";

        when(auth.getPrincipal()).thenReturn(User.builder()
                .username("testUser")
                .password("password123")
                .roles("USER").build());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(userService.findByUsername("testUser")).thenReturn(userEntity);
        when(jwtService.generateToken(userEntity)).thenReturn(generatedToken);
    }


    @Test
    void register_ShouldReturnRegisterResponseWithToken_WhenUserIsCreatedSuccessfully() {
        RegisterRequest request = new RegisterRequest("testUser", "password123");
        RegisterResponse expectedResponse = new RegisterResponse(generatedToken);

        when(userService.create("testUser", "password123")).thenReturn(userEntity);
        when(authMapper.toRegisterResponse(generatedToken)).thenReturn(expectedResponse);

        RegisterResponse actualResponse = authFacade.register(request);

        assertEquals(expectedResponse, actualResponse);
        verify(userService).create("testUser", "password123");
        verify(authMapper).toRegisterResponse(generatedToken);
    }

    @Test
    void login_ShouldReturnLoginResponseWithToken_WhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("testUser", "password123");
        LoginResponse expectedResponse = new LoginResponse(generatedToken);

        when(authMapper.toLoginResponse(generatedToken)).thenReturn(expectedResponse);

        LoginResponse actualResponse = authFacade.login(request);

        assertEquals(expectedResponse, actualResponse);
        verify(authMapper).toLoginResponse(generatedToken);
    }

}

