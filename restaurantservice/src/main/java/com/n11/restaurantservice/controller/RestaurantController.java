package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.repository.RestaurantRepository;
import com.n11.restaurantservice.request.CreateRestaurantRequest;
import com.n11.restaurantservice.request.UpdateRestaurantRequest;
import com.n11.restaurantservice.service.entityservice.RestaurantEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantControllerContract restaurantControllerContract;

    @GetMapping
    public ResponseEntity<Restaurant> getRestaurants() {
        /*Restaurant restaurant = new Restaurant();
        restaurant.setId("test");
        restaurant.setName("Restaurant 1");
        restaurant.setType(RestaurantType.TURKISH);
        Point location = new Point(Math.random() * 90, Math.random() * 90);
        restaurant.setLocation(location);
        restaurantRepository.save(restaurant);
        return ResponseEntity.ok(restaurant);*/
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RestaurantDTO>> getNearbyRestaurants(
            @RequestParam("latitude") @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
            @RequestParam("longitude") @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude,
            @RequestParam("distance") @NotNull @DecimalMin("0.1") @DecimalMax("100.0") Double distance) {
        List<RestaurantDTO> restaurants = restaurantControllerContract.getNearbyRestaurants(latitude, longitude, distance);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/with-name")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByName(
            @RequestParam("name")
            @NotBlank(message = "Name cannot be blank")
            @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
            String name
    ) {
        List<RestaurantDTO> restaurants = restaurantControllerContract.getRestaurantsByName(name);
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        RestaurantDTO restaurant = restaurantControllerContract.createRestaurant(request);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@NotNull @PathVariable String id) {
        RestaurantDTO restaurant = restaurantControllerContract.getRestaurant(id);
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@NotNull @PathVariable String id) {
        restaurantControllerContract.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable String id, @RequestBody UpdateRestaurantRequest request) {
        RestaurantDTO restaurant = restaurantControllerContract.updateRestaurant(id, request);
        return ResponseEntity.ok(restaurant);
    }
}
