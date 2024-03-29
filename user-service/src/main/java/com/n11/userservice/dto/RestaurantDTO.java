package com.n11.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record RestaurantDTO(
        String id,
        @Schema(description = "Restaurant's name", example = "Ocakbaşı Restaurant")
        String name,
        @Schema(description = "Restaurant's type", example = "TURKISH")
        String type,
        LocationDTO location,
        @Schema(description = "Distance from the user's/given location", example = "5.0", type = "number")
        Float distance
) implements Serializable {
}
