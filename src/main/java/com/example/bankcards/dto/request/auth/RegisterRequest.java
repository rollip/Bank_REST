package com.example.bankcards.dto.request.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
        @NotBlank(message = "Username can't be blank or null")
        private String username;

        @Size(min = 8, message = "Password has to be at least 8 characters long")
        private String password;
}