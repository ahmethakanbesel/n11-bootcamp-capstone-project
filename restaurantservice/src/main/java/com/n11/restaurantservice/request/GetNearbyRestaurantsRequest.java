package com.n11.restaurantservice.request;

public record GetNearbyRestaurantsRequest(
        Double latitude,
        Double longitude,
        Double distance
) {
}
