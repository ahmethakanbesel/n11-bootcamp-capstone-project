package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.repository.RestaurantRepository;
import com.n11.restaurantservice.service.entityservice.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public ResponseEntity<Restaurant> getRestaurants() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("test");
        restaurant.setName("Restaurant 1");
        restaurant.setType(RestaurantType.TURKISH);
        Point location = new Point(40.0, 32.0);
        restaurant.setLocation(location);
        restaurantRepository.save(restaurant);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/nearby")
    public ResponseEntity<String> getNearbyRestaurants(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude, @RequestParam("distance") Double distance) {
        return ResponseEntity.ok("Nearby Restaurants");
    }
}
