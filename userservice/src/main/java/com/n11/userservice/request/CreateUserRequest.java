package com.n11.userservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 63, message = "Name must be between 2 and 63 characters long")
        @Schema(description = "User's name", example = "John")
        String name,

        @NotBlank(message = "Surname is mandatory")
        @Size(min = 2, max = 63, message = "Surname must be between 2 and 63 characters long")
        @Schema(description = "User's surname", example = "Doe")
        String surname,

        @NotBlank(message = "Username is mandatory")
        @Size(min = 3, max = 15, message = "Username must be between 3 and 20 characters long")
        @Schema(description = "User's username", example = "john_doe")
        String username,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        @Schema(description = "User's email address", example = "john.doe@example.com")
        String email,

        @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Phone number is not valid")
        @Schema(description = "User's phone number", example = "+1234567890")
        String phoneNumber,

        @NotNull(message = "Birth date is mandatory")
        @Past(message = "Birth date should be in the past")
        @Schema(description = "User's birth date", example = "1990-01-01")
        LocalDate birthDate,

        @NotNull(message = "Latitude is mandatory")
        @Range(min = -90, max = 90)
        @Schema(description = "Latitude for the user's location", example = "40.7128")
        Double latitude,

        @NotNull(message = "Longitude is mandatory")
        @Range(min = -180, max = 180)
        @Schema(description = "Longitude for the user's location", example = "-74.0060")
        Double longitude
) {
}
