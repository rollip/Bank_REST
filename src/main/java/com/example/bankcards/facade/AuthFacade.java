package com.example.bankcards.facade;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;

public interface AuthFacade {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
