package com.example.bankcards.dto.request.card;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardRequestDto {
    @NotNull
    @Schema(
            description = "ID of the card owner",
            example = "22"
    )
    private Long ownerId;
}
