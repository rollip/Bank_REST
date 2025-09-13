package com.example.bankcards.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username can't be blank or null")
    private String username;
    @NotBlank(message = "Password can't be blank or null")
    private String password;
}
