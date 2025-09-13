package com.example.bankcards.mapper;

import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginResponse toLoginResponse(String token) {
        return new LoginResponse(token);
    }

    public RegisterResponse toRegisterResponse(String token) {
        return new RegisterResponse(token);
    }
}
