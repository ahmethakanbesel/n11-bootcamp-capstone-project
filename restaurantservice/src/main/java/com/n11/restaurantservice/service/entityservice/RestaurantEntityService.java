package com.n11.restaurantservice.service.entityservice;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.common.BaseEntityService;
import com.n11.restaurantservice.repository.RestaurantRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RestaurantEntityService extends BaseEntityService<Restaurant, RestaurantRepository> {
    private final RestaurantRepository restaurantRepository;

    protected RestaurantEntityService(RestaurantRepository restaurantRepository) {
        super(restaurantRepository);
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findByName(String name) {
        return restaurantRepository.findByNameContaining(name);
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
