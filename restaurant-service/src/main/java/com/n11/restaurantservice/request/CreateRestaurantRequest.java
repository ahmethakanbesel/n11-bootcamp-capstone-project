package com.n11.restaurantservice.request;

import com.n11.restaurantservice.enums.RestaurantType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CreateRestaurantRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 63, message = "Name must be between 3 and 63 characters")
        @Schema(description = "Restaurant's name", example = "Ocakbaşı Restaurant")
        String name,

        @NotNull(message = "Type cannot be null")
        @Schema(description = "Restaurant's type", example = "TURKISH")
        RestaurantType type,

        @NotNull(message = "Latitude is mandatory")
        @Range(min = -90, max = 90)
        @Schema(description = "Latitude for the restaurant's location", example = "39.925533")
        Double latitude,

        @NotNull(message = "Longitude is mandatory")
        @Range(min = -180, max = 180)
        @Schema(description = "Longitude for the restaurant's location", example = "32.866287")
        Double longitude
) {
}
