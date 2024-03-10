package com.n11.restaurantservice.service.entityservice;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.exception.ResourceNotFoundException;
import com.n11.restaurantservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        Assert.hasLength(restaurant.getName(), "Restaurant name must not be empty");

        return restaurantRepository.save(restaurant, Duration.ofSeconds(10));
    }

    public Optional<Restaurant> findById(String id) {
        Assert.notNull(id, "ID must not be null");

        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant", "id", id);
        }

        return restaurant;
    }

    public List<Restaurant> findByName(String name) {
        Assert.hasLength(name, "Name must not be empty");

        return restaurantRepository.findByName(name);
    }

    public List<Restaurant> findNearbyByType(RestaurantType type, double latitude, double longitude, double distance) {
        Assert.notNull(type, "Type must not be null");
        Assert.isTrue(latitude >= -90 && latitude <= 90, "Latitude must be between -90 and 90");
        Assert.isTrue(longitude >= -180 && longitude <= 180, "Longitude must be between -180 and 180");
        Assert.isTrue(distance > 0, "Distance must be positive");

        return restaurantRepository.findNearbyByType(type, latitude, longitude, distance);
    }

    public List<Restaurant> findNearby(double latitude, double longitude, double distance) {
        Assert.isTrue(latitude >= -90 && latitude <= 90, "Latitude must be between -90 and 90");
        Assert.isTrue(longitude >= -180 && longitude <= 180, "Longitude must be between -180 and 180");
        Assert.isTrue(distance > 0, "Distance must be positive");

        return restaurantRepository.findNearby(latitude, longitude, distance);
    }
}
