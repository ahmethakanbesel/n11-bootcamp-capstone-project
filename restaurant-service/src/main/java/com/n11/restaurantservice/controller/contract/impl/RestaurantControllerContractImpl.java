package com.n11.restaurantservice.controller.contract.impl;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.request.CreateRestaurantRequest;
import com.n11.restaurantservice.request.UpdateRestaurantRequest;
import com.n11.restaurantservice.service.entityservice.RestaurantEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantControllerContractImpl implements RestaurantControllerContract {
    private final RestaurantEntityService restaurantEntityService;

    @Override
    public List<RestaurantDTO> getAllRestaurants(int page, int pageLimit, String sortBy, String sortDir) {
        Page<Restaurant> restaurants = restaurantEntityService.findAll(page, pageLimit, sortBy, sortDir);

        return restaurants.stream()
                .map(RestaurantMapper::convertToRestaurantDTO)
                .toList();
    }

    @Override
    public List<RestaurantDTO> getNearbyRestaurants(double latitude, double longitude, double distance) {
        List<Restaurant> restaurants = restaurantEntityService.findNearby(latitude, longitude, distance);

        return restaurants.stream()
                .map(RestaurantMapper::convertToRestaurantDTOWithDistance)
                .toList();
    }

    @Override
    public List<RestaurantDTO> getRestaurantsByName(String name) {
        List<Restaurant> restaurants = restaurantEntityService.findByName(name);

        return restaurants.stream()
                .map(RestaurantMapper::convertToRestaurantDTO)
                .toList();
    }

    @Override
    public RestaurantDTO createRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = RestaurantMapper.convertCreateRestaurantRequestToRestaurant(request);
        restaurant = restaurantEntityService.save(restaurant);
        return RestaurantMapper.convertToRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO getRestaurant(String id) {
        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);
        return RestaurantMapper.convertToRestaurantDTO(restaurant);
    }

    @Override
    public RestaurantDTO updateRestaurant(String id, UpdateRestaurantRequest request) {
        Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);
        RestaurantMapper.updateRestaurantFields(restaurant, request);
        restaurant = restaurantEntityService.save(restaurant);
        return RestaurantMapper.convertToRestaurantDTO(restaurant);
    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantEntityService.delete(id);
    }
}
