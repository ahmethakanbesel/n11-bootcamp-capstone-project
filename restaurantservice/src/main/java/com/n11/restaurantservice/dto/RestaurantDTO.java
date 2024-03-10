package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.enums.RestaurantType;

public record RestaurantDTO(
        String id,
        String name,
        RestaurantType type,
        LocationDTO location,
        Float distance
) {
}
