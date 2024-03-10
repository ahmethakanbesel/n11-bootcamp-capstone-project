package com.n11.restaurantservice.dto;

import com.n11.restaurantservice.enums.RestaurantType;

import java.math.BigDecimal;

public record RestaurantDTO(
        String name,
        RestaurantType type,
        LocationDTO location,
        Integer distance
) {
}
