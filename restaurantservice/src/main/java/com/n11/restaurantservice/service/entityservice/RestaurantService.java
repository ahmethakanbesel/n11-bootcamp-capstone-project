package com.n11.restaurantservice.service.entityservice;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.exception.ResourceNotFoundException;
import com.n11.restaurantservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
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
        Point location = new Point(latitude, longitude);
        Distance d = new Distance(distance, Metrics.KILOMETERS);

        return restaurantRepository.findNearbyByType(type, location, d);
    }

    public List<Restaurant> findNearby(double latitude, double longitude, double distance) {
        Point location = new Point(latitude, longitude);
        Distance d = new Distance(distance, Metrics.KILOMETERS);

        return restaurantRepository.findNearby(location, d);
    }
}
