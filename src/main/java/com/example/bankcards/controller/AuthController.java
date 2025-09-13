package com.example.bankcards.controller;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.facade.AuthFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthFacade facade;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = facade.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = facade.login(request);
        return ResponseEntity.ok(response);
    }
}
