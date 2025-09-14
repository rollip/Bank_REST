package com.example.bankcards.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username can't be blank or null")
    @Schema(
            description = "Username",
            example = "rollip"
    )
    private String username;

    @NotBlank(message = "Password can't be blank or null")
    @Schema(
            description = "Password",
            example = "c0olP@ssw0rd!"
    )
    private String password;
}