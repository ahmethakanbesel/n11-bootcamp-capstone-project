package com.n11.userservice.dto;

import com.n11.userservice.enums.UserStatus;

import java.time.LocalDate;

public record UserDTO(
        Long id,
        String name,
        String surname,
        String username,
        String email,
        LocalDate birthDate,
        Double latitude,
        Double longitude,
        String phoneNumber,
        UserStatus status
) {
}