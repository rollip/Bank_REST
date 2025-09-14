package com.example.bankcards.controller;

import com.example.bankcards.dto.request.auth.LoginRequest;
import com.example.bankcards.dto.request.auth.RegisterRequest;
import com.example.bankcards.dto.response.auth.LoginResponse;
import com.example.bankcards.dto.response.auth.RegisterResponse;
import com.example.bankcards.facade.AuthFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthFacade facade;
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user account and returns a JWT token in the Authorization header."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful, token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = facade.register(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken())
                .body(response);
    }


    @Operation(
            summary = "User login",
            description = "Authenticates the user with email and password, returning a JWT token in the Authorization header."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = facade.login(request);
        return ResponseEntity.ok()
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken())
                                .body(response);
    }
}
