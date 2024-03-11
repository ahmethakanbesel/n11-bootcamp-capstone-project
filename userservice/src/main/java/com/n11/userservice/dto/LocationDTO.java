package com.n11.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LocationDTO(
        @Schema(description = "Latitude for the restaurant's location", example = "39.925533")
        Double latitude,
        @Schema(description = "Longitude for the restaurant's location", example = "32.866287")
        Double longitude
) {
}

