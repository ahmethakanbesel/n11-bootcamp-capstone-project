package com.n11.restaurantservice.controller.contract;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.request.CreateRestaurantRequest;
import com.n11.restaurantservice.request.UpdateRestaurantRequest;

import java.util.List;

public interface RestaurantControllerContract {
    List<RestaurantDTO> getAllRestaurants(int page, int pageLimit, String sortBy, String sortDir);

    List<RestaurantDTO> getNearbyRestaurants(double latitude, double longitude, double distance);

    List<RestaurantDTO> getRestaurantsByName(String name);

    RestaurantDTO createRestaurant(CreateRestaurantRequest request);

    RestaurantDTO getRestaurant(String id);

    RestaurantDTO updateRestaurant(String id, UpdateRestaurantRequest request);

    void deleteRestaurant(String id);
}