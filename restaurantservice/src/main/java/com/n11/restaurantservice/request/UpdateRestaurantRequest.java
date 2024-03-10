package com.n11.restaurantservice.request;

import com.n11.restaurantservice.enums.RestaurantType;

public record UpdateRestaurantRequest(
        String name,
        RestaurantType type,

        double latitude,
        double longitude
) {
}
