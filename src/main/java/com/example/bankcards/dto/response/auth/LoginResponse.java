package com.example.bankcards.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response returned after successful login, contains JWT token")
public class LoginResponse {
    @Schema(description = "JWT token (in auth header too)",
           example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
