package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.dto.LocationDTO;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class RestaurantMapper {
    private RestaurantMapper() {
    }

    public static RestaurantDTO convertToRestaurantDTO(Restaurant restaurant) {
        LocationDTO location = new LocationDTO(restaurant.getLocation().getX(), restaurant.getLocation().getY());
        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getType(), location, restaurant.getScore());
    }
}