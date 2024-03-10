package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.enums.RestaurantType;
import io.swagger.v3.oas.annotations.media.Schema;

public record RestaurantDTO(
        String id,
        @Schema(description = "Restaurant's name", example = "Ocakbaşı Restaurant")
        String name,
        @Schema(description = "Restaurant's type", example = "TURKISH")
        RestaurantType type,
        LocationDTO location,
        Float distance
) {
}
