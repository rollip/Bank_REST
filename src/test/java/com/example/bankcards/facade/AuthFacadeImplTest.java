package com.example.bankcards.facade;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.facade.impl.AuthFacadeImpl;
import com.example.bankcards.mapper.AuthMapper;
import com.example.bankcards.security.CardUserDetails;
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
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthFacadeImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthFacadeImpl authFacade;

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password123";
    private static final String JWT_TOKEN = "mocked-jwt-token";
    private static final String ROLE_USER = "USER";

    private UserEntity userEntity;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .username(USERNAME)
                .build();

        userDetails = new CardUserDetails(1L, USERNAME,PASSWORD, List.of(ROLE_USER));
        authFacade = new AuthFacadeImpl(authenticationManager,jwtService,userService,new AuthMapper());
    }

    @Test
    void register_ShouldReturnValidTokenAndCreateUser_WhenRequestIsValid() {
        RegisterRequest request = new RegisterRequest(USERNAME, PASSWORD);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userService.findByUsername(USERNAME)).thenReturn(userEntity);
        when(jwtService.generateToken(userEntity)).thenReturn(JWT_TOKEN);
        when(userService.create(USERNAME, PASSWORD)).thenReturn(userEntity);

        RegisterResponse response = authFacade.register(request);

        assertEquals(JWT_TOKEN, response.getToken());
    }

    @Test
    void login_ShouldReturnValidTokenAndAuthenticateUser_WhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest(USERNAME, PASSWORD);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userService.findByUsername(USERNAME)).thenReturn(userEntity);
        when(jwtService.generateToken(userEntity)).thenReturn(JWT_TOKEN);

        LoginResponse response = authFacade.login(request);

        assertEquals(JWT_TOKEN, response.getToken());
    }
}

