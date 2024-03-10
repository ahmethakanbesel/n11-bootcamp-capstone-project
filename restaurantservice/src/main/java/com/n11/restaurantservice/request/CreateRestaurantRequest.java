package com.n11.restaurantservice.request;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;

public record CreateRestaurantRequest(
        String name,
        RestaurantType type,
        Double latitude,
        Double longitude
) {
}
