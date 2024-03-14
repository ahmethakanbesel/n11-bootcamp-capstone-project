package com.n11.restaurantservice.service.entityservice;

import com.n11.restaurantservice.common.BaseEntityService;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.repository.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantEntityService extends BaseEntityService<Restaurant, RestaurantRepository> {
    private final RestaurantRepository restaurantRepository;

    protected RestaurantEntityService(RestaurantRepository restaurantRepository) {
        super(restaurantRepository);
        this.restaurantRepository = restaurantRepository;
    }

    public Page<Restaurant> findAll(int page, int size, String sort, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
        return restaurantRepository.findAll(pageable);
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
        Sort sort = Sort.by(Sort.Direction.ASC, "score");

        return restaurantRepository.findNearby(location, d, sort);
    }
}
