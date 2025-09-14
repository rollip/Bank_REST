package com.example.bankcards.dto.request.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

        @NotBlank(message = "Username can't be blank or null")
        @Schema(
                description = "Username",
                example = "rollip"
        )
        private String username;

        @Size(min = 8, message = "Password has to be at least 8 characters long")
        @Schema(
                description = "Password (minimum 8 characters)",
                example = "c0olP@ssw0rd!"
        )
        private String password;
}