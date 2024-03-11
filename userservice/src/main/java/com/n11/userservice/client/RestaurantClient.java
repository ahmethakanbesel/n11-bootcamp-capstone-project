package com.n11.userservice.client;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "restaurant-service", url = "${restaurant-service.url}")
public interface RestaurantClient {
    RestResponse<List<RestaurantDTO>> getNearbyRestaurants(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double distance);
}
