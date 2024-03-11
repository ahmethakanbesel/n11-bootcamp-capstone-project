package com.n11.userservice.dto;

import com.n11.userservice.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserDTO(
        Long id,
        @Schema(description = "User's name", example = "John")
        String name,
        @Schema(description = "User's surname", example = "Doe")
        String surname,
        @Schema(description = "User's username", example = "johndoe")
        String username,
        @Schema(description = "User's email", example = "johndoe@mail.com")
        String email,
        @Schema(description = "User's birth date", example = "1990-01-01")
        LocalDate birthDate,
        @Schema(description = "Latitude for the user's location", example = "39.925593", type = "number")
        Double latitude,
        @Schema(description = "Longitude for the user's location", example = "32.866237", type = "number")
        Double longitude,
        @Schema(description = "User's phone number", example = "+1234567890")
        String phoneNumber,
        @Schema(description = "User's status", example = "ACTIVE")
        UserStatus status
) {
}