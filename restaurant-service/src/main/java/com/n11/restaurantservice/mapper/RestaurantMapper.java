package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.dto.LocationDTO;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.request.CreateRestaurantRequest;
import com.n11.restaurantservice.request.UpdateRestaurantRequest;
import org.springframework.data.geo.Point;

public class RestaurantMapper {
    private RestaurantMapper() {
    }

    public static RestaurantDTO convertToRestaurantDTO(Restaurant restaurant) {
        LocationDTO location = new LocationDTO(restaurant.getLocation().getX(), restaurant.getLocation().getY());
        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getType(), location, null);
    }

    public static RestaurantDTO convertToRestaurantDTOWithDistance(Restaurant restaurant) {
        LocationDTO location = new LocationDTO(restaurant.getLocation().getX(), restaurant.getLocation().getY());
        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getType(), location, restaurant.getScore());
    }

    public static Restaurant convertCreateRestaurantRequestToRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.name());
        restaurant.setType(request.type());
        restaurant.setLocation(new Point(request.latitude(), request.longitude()));
        return restaurant;
    }

    public static void updateRestaurantFields(Restaurant restaurant, UpdateRestaurantRequest request) {
        restaurant.setName(request.name());
        restaurant.setType(request.type());
        restaurant.setLocation(new Point(request.latitude(), request.longitude()));
    }
}