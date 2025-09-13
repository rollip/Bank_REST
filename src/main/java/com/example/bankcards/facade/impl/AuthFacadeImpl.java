package com.example.bankcards.facade.impl;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.facade.AuthFacade;
import com.example.bankcards.mapper.AuthMapper;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthMapper authMapper;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        userService.create(request.getUsername(), request.getPassword());
        String token = buildToken(request.getUsername(), request.getPassword());
        return authMapper.toRegisterResponse(token);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String token = buildToken(loginRequest.getUsername(), loginRequest.getPassword());
        return authMapper.toLoginResponse(token);
    }

    private String buildToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String authenticatedUsername = ((UserDetails) authentication.getPrincipal()).getUsername();

        UserEntity user = userService.findByUsername(authenticatedUsername);

        return jwtService.generateToken(user);
    }
}

